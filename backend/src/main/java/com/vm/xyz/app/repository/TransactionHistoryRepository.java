package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query(
            value = "select * from transaction_history where machine_id = :machineId and CAST(created as date) = CAST(:date as date)",
            nativeQuery = true)
    List<TransactionHistory> findTransactionHistories(@Param("machineId") Long machineId, @Param("date") Date date);

}
