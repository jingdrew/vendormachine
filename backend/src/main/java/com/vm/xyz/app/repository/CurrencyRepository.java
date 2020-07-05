package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
