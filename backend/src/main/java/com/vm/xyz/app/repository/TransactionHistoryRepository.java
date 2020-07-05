package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query("select t from TransactionHistory t where machine_id = :machineId and CAST(t.created as date) = CAST(:date as date)")
    List<TransactionHistory> findByMachineIdAndByDate(Long machineId, Date date);

}
