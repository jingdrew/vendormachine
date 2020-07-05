package com.vm.xyz.app.api.v1;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.MachineProductSlot;
import com.vm.xyz.app.model.TransactionResult;
import com.vm.xyz.app.service.MachineService;
import com.vm.xyz.app.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.v1}/machine")
public class MachineController {

    private final MachineService machineService;
    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public Machine getMachine(@PathVariable("id") Long machineId) {
        return machineService.getMachineById(machineId);
    }

    @GetMapping("/list")
    public List<Machine> getMachines() {
        return machineService.getMachines();
    }

    @GetMapping("/pslot/list")
    public List<MachineProductSlot> getMachineProductSlots(@RequestParam("machine") Long machineId) {
        return machineService.getMachineProductSlots(machineId);
    }

    @PostMapping("/pslot")
    public MachineProductSlot saveMachineProductSlot(@RequestBody MachineProductSlot productSlot) {
        return machineService.saveMachineProductSlot(productSlot);
    }

    @GetMapping("/mslot/list")
    public List<MachineMoneySlot> getMachineMoneySlots(@RequestParam("machine") Long machineId) {
        return machineService.getMachineMoneySlots(machineId);
    }

    @PostMapping("/mlot")
    public MachineMoneySlot saveMachineMoneySlot(@RequestBody MachineMoneySlot moneySlot) {
        return machineService.saveMachineMoneySlot(moneySlot);
    }

    @PostMapping("/credit/add")
    public TransactionResult addCredit(@RequestParam("machine") Long machineId, @RequestParam("amount")BigDecimal amount) {
        return transactionService.addCredit(machineId, amount);
    }

    @PostMapping("/credit/withdraw")
    public TransactionResult withdrawCredit(@RequestParam("machine") Long machineId) {
        return transactionService.withdrawCredits(machineId);
    }

}
