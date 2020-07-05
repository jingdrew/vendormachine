package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
}
