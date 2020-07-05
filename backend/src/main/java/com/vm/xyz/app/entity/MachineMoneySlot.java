package com.vm.xyz.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vm.xyz.app.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineMoneySlot extends BaseEntity {

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Machine machine;

    @OneToOne
    private Currency currency;

    private Owner owner;
    private int qty;
}
