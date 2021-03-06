package com.vm.xyz.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XYZUser extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private int loginAttempts;


}
