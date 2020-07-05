package com.vm.xyz.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class MachineProductSlot extends BaseEntity {

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Machine machine;

    @OneToOne
    private Product product;

    private int qty;

    public void addQty(int qty) {
        this.qty += qty;
    }
}
