package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.TransactionHistory;
import com.vm.xyz.app.repository.TransactionHistoryRepository;
import com.vm.xyz.app.service.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionHistory> getTransactionHistories(Long machineId, Date date) {
        return transactionHistoryRepository.findTransactionHistories(machineId, date);
    }

}
