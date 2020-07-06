package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.XYZUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<XYZUser, Long> {

}
