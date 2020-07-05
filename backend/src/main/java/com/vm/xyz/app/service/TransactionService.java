package com.vm.xyz.app.service;

import com.vm.xyz.app.model.TransactionResult;

import java.math.BigDecimal;

public interface TransactionService {

    TransactionResult addCredit(Long machineId, BigDecimal amount);

    TransactionResult withdrawCredits(Long machineId);

}
