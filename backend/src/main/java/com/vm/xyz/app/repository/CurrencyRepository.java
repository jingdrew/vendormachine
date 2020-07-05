package com.vm.xyz.app.repository;

import com.vm.xyz.app.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByValueAndAbbreviation(BigDecimal amount, String abbr);
}
