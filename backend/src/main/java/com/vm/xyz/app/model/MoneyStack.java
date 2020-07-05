package com.vm.xyz.app.model;

import com.vm.xyz.app.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoneyStack {
    private Currency currency;
    private int qty;
}
