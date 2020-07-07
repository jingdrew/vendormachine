package com.vm.xyz.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    private String email;
    private String message;
}
