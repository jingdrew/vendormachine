package com.vm.xyz.app.api.v1;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.model.Payment;
import com.vm.xyz.app.model.PaymentResult;
import com.vm.xyz.app.service.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.v1}/machine")
public class MachineController {

    private final MachineService machineService;

    @GetMapping("/{id}")
    public Machine getMachine(@PathVariable("id") Long machineId) {
        return machineService.getMachineById(machineId);
    }

    @GetMapping("/list")
    public List<Machine> getMachines() {
        return machineService.getMachines();
    }

    @PostMapping("/transaction/buy")
    public PaymentResult buyProduct(@RequestParam("machine") Long machineId,
                                    @RequestParam("slot") Long productSlotId,
                                    @RequestBody Payment payment) {
        return machineService.buyProduct(machineId, productSlotId, payment);
    }


}
