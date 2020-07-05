package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;
import com.vm.xyz.app.entity.TransactionHistory;
import com.vm.xyz.app.exception.BadRequestException;
import com.vm.xyz.app.exception.NoDataFoundException;
import com.vm.xyz.app.model.Payment;
import com.vm.xyz.app.model.PaymentMethod;
import com.vm.xyz.app.model.PaymentResult;
import com.vm.xyz.app.repository.MachineMoneySlotRepository;
import com.vm.xyz.app.repository.MachineProductSlotRepository;
import com.vm.xyz.app.repository.MachineRepository;
import com.vm.xyz.app.repository.TransactionHistoryRepository;
import com.vm.xyz.app.service.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<MachineProductSlot> saveMachineProductSlots(List<MachineProductSlot> productSlots) {
        return machineProductSlotRepository.saveAll(productSlots);
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
    public List<MachineMoneySlot> saveMachineMoneySlots(List<MachineMoneySlot> moneySlots) {
        return machineMoneySlotRepository.saveAll(moneySlots);
    }

    @Override
    public PaymentResult buyProduct(Long machineId, Long productSlotId, Payment payment) {
        if (payment.getPaymentMethod() == PaymentMethod.CARD) {
            return processPaymentWithCard(machineId, productSlotId);
        } else if (payment.getPaymentMethod() == PaymentMethod.CASH) {

        } else {
            throw new BadRequestException("Invalid payment method");
        }
        return null;
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

                return new PaymentResult(new ArrayList<>(), transactionHistory);
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

                return new PaymentResult(new ArrayList<>(), transactionHistory);
            }
        } else {
            throw new BadRequestException("Product is sold out.");
        }
    }


}
