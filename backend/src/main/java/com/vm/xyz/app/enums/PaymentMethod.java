package com.vm.xyz.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("CASH"),
    CARD("CARD"),
    ALL("ALL");

    private final String paymentMethod;
}
