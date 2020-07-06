package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;
import com.vm.xyz.app.entity.TransactionHistory;
import com.vm.xyz.app.enums.CurrencyType;
import com.vm.xyz.app.enums.PaymentMethod;
import com.vm.xyz.app.exception.BadRequestException;
import com.vm.xyz.app.exception.NoDataFoundException;
import com.vm.xyz.app.model.*;
import com.vm.xyz.app.repository.MachineMoneySlotRepository;
import com.vm.xyz.app.repository.MachineProductSlotRepository;
import com.vm.xyz.app.repository.MachineRepository;
import com.vm.xyz.app.repository.TransactionHistoryRepository;
import com.vm.xyz.app.service.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final MachineMoneySlotRepository machineMoneySlotRepository;
    private final MachineProductSlotRepository machineProductSlotRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public Machine getMachineById(Long machineId) {
        return machineRepository.findById(machineId).orElseThrow(
                () -> new NoDataFoundException("Machine with id of " + machineId + " was not found or does not exist."));
    }

    @Override
    public List<Machine> getMachines() {
        return machineRepository.findAll();
    }

    @Override
    public Machine saveMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public MachineProductSlot saveMachineProductSlot(MachineProductSlot productSlot) {
        return machineProductSlotRepository.save(productSlot);
    }

    @Override
    public List<MachineProductSlot> getMachineProductSlots(Long machineId) {
        return machineProductSlotRepository.findByMachineId(machineId);
    }

    @Override
    public Machine saveMachineProductSlots(Long machineId, List<MachineProductSlot> productSlots) {
        Machine machine = getMachineById(machineId);
        for (MachineProductSlot slot : productSlots) {
            slot.setMachine(machine);
        }
        machine.setProductSlotList(machineProductSlotRepository.saveAll(productSlots));
        return machine;
    }

    @Override
    public MachineMoneySlot saveMachineMoneySlot(MachineMoneySlot moneySlot) {
        return machineMoneySlotRepository.save(moneySlot);
    }

    @Override
    public List<MachineMoneySlot> getMachineMoneySlots(Long machineId) {
        return machineMoneySlotRepository.findByMachineId(machineId);
    }

    @Override
    public Machine saveMachineMoneySlots(Long machineId, List<MachineMoneySlot> moneySlots) {
        Machine machine = getMachineById(machineId);
        for (MachineMoneySlot slot : moneySlots) {
            slot.setMachine(machine);
        }
        machine.setMoneySlotList(machineMoneySlotRepository.saveAll(moneySlots));
        return machine;
    }

    @Override
    public PaymentResult buyProduct(Long machineId, Long productSlotId, Payment payment) {
        if (payment.getPaymentMethod() == PaymentMethod.CARD) {
            return processPaymentWithCard(machineId, productSlotId);
        } else if (payment.getPaymentMethod() == PaymentMethod.CASH) {
            return processPaymentWithCash(machineId, productSlotId, payment);
        } else {
            throw new BadRequestException("Invalid payment method");
        }
    }

    private PaymentResult processPaymentWithCash(Long machineId, Long productSlotId, Payment payment) {
        MachineProductSlot productSlot = machineProductSlotRepository.findById(productSlotId).orElseThrow(
                () -> new BadRequestException("Selected product is invalid"));
        Machine machine = machineRepository.findById(machineId).orElseThrow(
                () -> new BadRequestException("Selected vendor machine is invalid"));
        if (productSlot.getQty() > 0) {
            BigDecimal totalCredits = BigDecimal.ZERO;
            for (MoneyStack stack : payment.getMoneyStacks()) {
                BigDecimal credit = stack.getCurrency().getValue().multiply(new BigDecimal(stack.getQty()));
                totalCredits = totalCredits.add(credit);
            }
            if (totalCredits.compareTo(productSlot.getProduct().getPrice()) >= 0) {
                BigDecimal change = totalCredits.subtract(productSlot.getProduct().getPrice());
                transferCredits(machine.getMoneySlotList(), payment.getMoneyStacks());
                List<MoneyStack> changeStackList = new ArrayList<>();
                calculateChanges(change, machine.getMoneySlotList(), changeStackList);
                productSlot.setQty(productSlot.getQty() - 1);
                machineProductSlotRepository.save(productSlot);
                machineMoneySlotRepository.saveAll(machine.getMoneySlotList());
                TransactionHistory transactionHistory = transactionHistoryRepository.save(
                        new TransactionHistory(
                                productSlot.getProduct(),
                                machine,
                                PaymentMethod.CASH,
                                machine.getModel(),
                                productSlot.getProduct().getName(),
                                productSlot.getProduct().getCost(),
                                productSlot.getProduct().getPrice(),
                                "SUCCESS",
                                ""));

                return new PaymentResult(changeStackList, transactionHistory, totalCredits, change);

            } else {
                transactionHistoryRepository.save(
                        new TransactionHistory(
                                productSlot.getProduct(),
                                machine,
                                PaymentMethod.CASH,
                                machine.getModel(),
                                productSlot.getProduct().getName(),
                                productSlot.getProduct().getCost(),
                                productSlot.getProduct().getPrice(),
                                "FAIL",
                                "Insufficient funds"));
                throw new BadRequestException("Insufficient funds");
            }
        } else {
            transactionHistoryRepository.save(
                    new TransactionHistory(
                            productSlot.getProduct(),
                            machine,
                            PaymentMethod.CASH,
                            machine.getModel(),
                            productSlot.getProduct().getName(),
                            productSlot.getProduct().getCost(),
                            productSlot.getProduct().getPrice(),
                            "FAIL",
                            "Product Sold out"));
            throw new BadRequestException("Product is sold out");
        }
    }

    private void transferCredits(List<MachineMoneySlot> moneySlots, List<MoneyStack> moneyStackList) {
        for (MoneyStack stack : moneyStackList) {
            for (MachineMoneySlot slot : moneySlots) {
                if (slot.getCurrency().getValue().compareTo(stack.getCurrency().getValue()) == 0) {
                    slot.setQty(slot.getQty() + 1);
                    break;
                }
            }
        }
    }

    private void calculateChanges(BigDecimal credits,
                                  List<MachineMoneySlot> machineMoneySlots,
                                  List<MoneyStack> changeStackList) {
        machineMoneySlots.sort((o1, o2) -> o2.getCurrency().getValue().compareTo(o1.getCurrency().getValue()));
        for (MachineMoneySlot slot : machineMoneySlots) {
            if (credits.compareTo(BigDecimal.ZERO) == 0)
                break;
            if (slot.getCurrency().getCurrencyType() == CurrencyType.COIN) {
                int qty = slot.getQty();
                if (qty > 0) {
                    BigDecimal coinValue = slot.getCurrency().getValue();
                    BigDecimal remainder = credits.remainder(coinValue);
                    BigDecimal tempCredit = credits.subtract(remainder);
                    BigDecimal coinValueTotal = coinValue.multiply(new BigDecimal(qty));
                    int qtyRequired = tempCredit.divide(coinValue).intValueExact();
                    if (qty >= qtyRequired) {
                        slot.setQty(slot.getQty() - qtyRequired);
                        credits = remainder;
                        changeStackList.add(new MoneyStack(slot.getCurrency(), qtyRequired));
                    } else {
                        tempCredit = tempCredit.subtract(coinValueTotal);
                        credits = tempCredit.add(remainder);
                        changeStackList.add(new MoneyStack(slot.getCurrency(), qty));
                    }
                }
            }
        }
        if (credits.compareTo(BigDecimal.ZERO) > 0) {
            throw new BadRequestException("The machine is out of change");
        }
    }

    private PaymentResult processPaymentWithCard(Long machineId, Long productSlotId) {
        MachineProductSlot productSlot = machineProductSlotRepository.findById(productSlotId).orElseThrow(
                () -> new BadRequestException("Selected product is invalid"));
        Machine machine = machineRepository.findById(machineId).orElseThrow(
                () -> new BadRequestException("Selected vendor machine is invalid"));
        int randNumber = new Random().nextInt(10);
        if (productSlot.getQty() > 0) {
            if (randNumber < 8) {
                productSlot.setQty(productSlot.getQty() - 1);
                machineProductSlotRepository.save(productSlot);
                TransactionHistory transactionHistory = transactionHistoryRepository.save(
                        new TransactionHistory(
                                productSlot.getProduct(),
                                machine,
                                PaymentMethod.CARD,
                                machine.getModel(),
                                productSlot.getProduct().getName(),
                                productSlot.getProduct().getCost(),
                                productSlot.getProduct().getPrice(),
                                "SUCCESS",
                                ""));

                return new PaymentResult(new ArrayList<>(), transactionHistory, BigDecimal.ZERO, BigDecimal.ZERO);
            } else {
                TransactionHistory transactionHistory = transactionHistoryRepository.save(
                        new TransactionHistory(
                                productSlot.getProduct(),
                                machine,
                                PaymentMethod.CARD,
                                machine.getModel(),
                                productSlot.getProduct().getName(),
                                productSlot.getProduct().getCost(),
                                productSlot.getProduct().getPrice(),
                                "FAIL",
                                "Insufficient Funds"));

                return new PaymentResult(new ArrayList<>(), transactionHistory, BigDecimal.ZERO, BigDecimal.ZERO);
            }
        } else {
            throw new BadRequestException("Product is sold out.");
        }
    }


}
