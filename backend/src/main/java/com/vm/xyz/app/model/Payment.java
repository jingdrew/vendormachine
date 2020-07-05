package com.vm.xyz.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private PaymentMethod paymentMethod;
    private List<MoneyStack> moneyStacks;
}
