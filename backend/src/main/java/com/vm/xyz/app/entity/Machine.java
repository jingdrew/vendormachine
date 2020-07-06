package com.vm.xyz.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vm.xyz.app.model.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Machine extends BaseEntity {

    private String model;
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentMethod acceptedPaymentMethod;

    @OneToMany(mappedBy = "machine")
    @OrderBy("id asc")
    @JsonManagedReference
    private List<MachineProductSlot> productSlotList;

    @OneToMany(mappedBy = "machine")
    @OrderBy("id asc")
    @JsonManagedReference
    private List<MachineMoneySlot> moneySlotList;

    public Machine(String model, String description, PaymentMethod acceptedPaymentMethod){
        this.model = model;
        this.description = description;
        this.acceptedPaymentMethod = acceptedPaymentMethod;
    }
}
