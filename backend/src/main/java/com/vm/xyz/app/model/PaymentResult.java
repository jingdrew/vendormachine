package com.vm.xyz.app.model;

import com.vm.xyz.app.entity.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResult {
    private List<MoneyStack> moneyStacksList;
    private TransactionHistory transactionHistory;
    private BigDecimal payWith;
    private BigDecimal change;
}
