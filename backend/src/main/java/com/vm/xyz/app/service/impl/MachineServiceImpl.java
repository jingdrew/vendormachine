package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;
import com.vm.xyz.app.exception.NoDataFoundException;
import com.vm.xyz.app.repository.MachineMoneySlotRepository;
import com.vm.xyz.app.repository.MachineProductSlotRepository;
import com.vm.xyz.app.repository.MachineRepository;
import com.vm.xyz.app.service.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final MachineMoneySlotRepository machineMoneySlotRepository;
    private final MachineProductSlotRepository machineProductSlotRepository;

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
}
