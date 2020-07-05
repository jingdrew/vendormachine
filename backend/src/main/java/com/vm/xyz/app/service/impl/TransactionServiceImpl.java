package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.Currency;
import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.exception.BadRequestException;
import com.vm.xyz.app.model.Changes;
import com.vm.xyz.app.model.Owner;
import com.vm.xyz.app.model.TransactionResult;
import com.vm.xyz.app.repository.CurrencyRepository;
import com.vm.xyz.app.repository.MachineMoneySlotRepository;
import com.vm.xyz.app.repository.MachineRepository;
import com.vm.xyz.app.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final MachineRepository machineRepository;
    private final CurrencyRepository currencyRepository;
    private final MachineMoneySlotRepository machineMoneySlotRepository;

    @Override
    public TransactionResult addCredit(Long machineId, BigDecimal amount) {
        Machine machine = machineRepository.findById(machineId).orElseThrow(
                () -> new BadRequestException("Machine with id of " + machineId + " was not found or does not exist."));
        Currency currency = currencyRepository.findByValueAndAbbreviation(amount, "USD").orElseThrow(
                () -> new BadRequestException("The amount of credit was not accepted."));
        MachineMoneySlot moneySlot = machineMoneySlotRepository
                .findByMachineIdAndCurrencyId(machineId, currency.getId())
                .orElseGet(()-> new MachineMoneySlot(machine, currency, Owner.CLIENT, 0));
        moneySlot.addQty(1);
        machineMoneySlotRepository.save(moneySlot);
        Machine newMachine = machineRepository.findById(machineId).orElseThrow(
                () -> new BadRequestException("Machine with id of " + machineId + " was not found or does not exist."));
        TransactionResult transactionResult = new TransactionResult();
        transactionResult.setMachine(machine);
        return transactionResult;
    }

    @Override
    public TransactionResult withdrawCredits(Long machineId) {
        List<MachineMoneySlot> moneySlotList = machineMoneySlotRepository.findByMachineIdAndOwner(machineId, Owner.CLIENT);
        BigDecimal totalCredits = calculateTotalMoney(moneySlotList);
        List<Changes> changesList = new ArrayList<>();
        if (totalCredits.compareTo(BigDecimal.ZERO) > 0) {
            for (MachineMoneySlot moneySlot : moneySlotList) {
                if (moneySlot.getQty() > 0) {
                    changesList.add(new Changes(moneySlot.getCurrency(), moneySlot.getQty()));
                    moneySlot.setQty(0);
                }
            }
            machineMoneySlotRepository.saveAll(moneySlotList);
        }
        Machine machine = machineRepository.findById(machineId).orElseThrow(
                () -> new BadRequestException("Machine with id of " + machineId + " was not found or does not exist."));
        TransactionResult result = new TransactionResult(changesList, machine);
        return result;
    }

    private BigDecimal calculateTotalMoney(List<MachineMoneySlot> moneySlotList) {
        BigDecimal totalCredits = BigDecimal.ZERO;
        for (MachineMoneySlot moneySlot : moneySlotList) {
            BigDecimal credit = moneySlot.getCurrency().getValue().multiply(new BigDecimal(moneySlot.getQty()));
            totalCredits = totalCredits.add(credit);
        }
        return totalCredits;
    }




}
