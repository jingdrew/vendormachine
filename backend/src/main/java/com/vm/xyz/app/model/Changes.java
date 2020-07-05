package com.vm.xyz.app.model;

import com.vm.xyz.app.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Changes {
    private Currency currency;
    private int qty;
}
