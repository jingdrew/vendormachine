package com.vm.xyz.app.job;

import com.vm.xyz.app.entity.Machine;
import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.entity.Notification;
import com.vm.xyz.app.repository.MachineMoneySlotRepository;
import com.vm.xyz.app.repository.MachineRepository;
import com.vm.xyz.app.repository.NotificationRepository;
import com.vm.xyz.app.repository.XYZUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class NotifyAdminSchedulerJob {

    private final MachineRepository machineRepository;
    private final NotificationRepository notificationRepository;
    private final XYZUserRepository userRepository;
    private final MachineMoneySlotRepository machineMoneySlotRepository;

    @Scheduled(fixedDelay = 3600000L)
    public void checkForMachines() {
        List<Machine> machineList = machineRepository.findAll();
        for (Machine machine: machineList) {
            List<MachineMoneySlot> slots = machineMoneySlotRepository.findByMachineId(machine.getId());
            BigDecimal totalCredits = calculateTotalCredits(slots);
            if (totalCredits.compareTo(new BigDecimal(100)) >= 0) {
                sendNotification(machine);
            }
        }
    }

    private void sendNotification(Machine machine) {
        userRepository.findByUsername(machine.getModel()).ifPresent(user -> {
            notificationRepository.save(new Notification(
                            user.getEmail(),
                            "Please check on machine " + machine.getModel() + ", money slot is almost full.")
            );
        });
    }

    private BigDecimal calculateTotalCredits(List<MachineMoneySlot> moneySlots) {
        BigDecimal totalCredits = BigDecimal.ZERO;
        for (MachineMoneySlot slot: moneySlots) {
            BigDecimal credit = slot.getCurrency().getValue().multiply(new BigDecimal(slot.getQty()));
            totalCredits = totalCredits.add(credit);
        }
        return totalCredits;
    }
}
