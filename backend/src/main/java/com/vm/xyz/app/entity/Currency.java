package com.vm.xyz.app.entity;

import com.vm.xyz.app.model.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    private String abbreviation;
    private String valueName;
    private BigDecimal value;
}
