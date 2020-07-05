package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.MachineProductSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineProductSlotRepository extends JpaRepository<MachineProductSlot, Long> {

    List<MachineProductSlot> findByMachineId(Long machineId);

}
