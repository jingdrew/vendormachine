package com.vm.xyz.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {
    COIN("COIN"),
    BILL("BILL");

    private final String currencyType;
}
