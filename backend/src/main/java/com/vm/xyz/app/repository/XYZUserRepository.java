package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.XYZUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XYZUserRepository extends JpaRepository<XYZUser, Long> {

    Optional<XYZUser> findByUsername(String username);
    
}
