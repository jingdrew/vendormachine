package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.MachineMoneySlot;
import com.vm.xyz.app.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MachineMoneySlotRepository extends JpaRepository<MachineMoneySlot, Long> {

    List<MachineMoneySlot> findByMachineId(Long machineId);

    List<MachineMoneySlot> findByMachineIdAndOwner(Long machineId, Owner owner);

    Optional<MachineMoneySlot> findByMachineIdAndCurrencyId(Long machineId, Long currencyId);
}
