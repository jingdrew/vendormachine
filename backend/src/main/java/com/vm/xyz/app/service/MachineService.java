package com.vm.xyz.app.service;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;
import com.vm.xyz.app.model.Payment;
import com.vm.xyz.app.model.PaymentResult;

import java.util.List;

public interface MachineService {

    Machine getMachineById(Long machineId);

    List<Machine> getMachines();

    Machine saveMachine(Machine machine);

    MachineProductSlot saveMachineProductSlot(MachineProductSlot productSlot);

    List<MachineProductSlot> getMachineProductSlots(Long machineId);

    Machine saveMachineProductSlots(Long machineId, List<MachineProductSlot> productSlots);

    MachineMoneySlot saveMachineMoneySlot(MachineMoneySlot moneySlot);

    List<MachineMoneySlot> getMachineMoneySlots(Long machineId);

    Machine saveMachineMoneySlots(Long machineId, List<MachineMoneySlot> moneySlots);

    PaymentResult buyProduct(Long machineId, Long productSlotId, Payment payment);

}
