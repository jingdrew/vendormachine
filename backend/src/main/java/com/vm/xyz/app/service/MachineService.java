package com.vm.xyz.app.service;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;

import java.util.List;

public interface MachineService {

    Machine getMachineById(Long machineId);

    List<Machine> getMachines();

    Machine saveMachine(Machine machine);

    MachineProductSlot saveMachineProductSlot(MachineProductSlot productSlot);

    List<MachineProductSlot> getMachineProductSlots(Long machineId);

    List<MachineProductSlot> saveMachineProductSlots(List<MachineProductSlot> productSlots);

    MachineMoneySlot saveMachineMoneySlot(MachineMoneySlot moneySlot);

    List<MachineMoneySlot> getMachineMoneySlots(Long machineId);

    List<MachineMoneySlot> saveMachineMoneySlots(List<MachineMoneySlot> moneySlots);
}
