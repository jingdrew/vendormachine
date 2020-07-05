package com.vm.xyz.app.service;

import com.vm.xyz.app.entity.TransactionHistory;

import java.util.Date;
import java.util.List;

public interface TransactionHistoryService {

    List<TransactionHistory> getTransactionHistories(Long machineId, Date date);

}
