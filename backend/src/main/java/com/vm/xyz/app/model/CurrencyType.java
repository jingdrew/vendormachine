package com.vm.xyz.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {
    COIN("COIN"),
    BILL("BILL");

    private final String currencyType;
}
