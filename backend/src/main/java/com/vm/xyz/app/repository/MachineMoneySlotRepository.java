package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.MachineMoneySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineMoneySlotRepository extends JpaRepository<MachineMoneySlot, Long> {

    List<MachineMoneySlot> findByMachineId(Long machineId);

}
