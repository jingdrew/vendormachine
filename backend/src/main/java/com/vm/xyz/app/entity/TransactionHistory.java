package com.vm.xyz.app.entity;

import com.vm.xyz.app.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    private Machine machine;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String machineName;
    private String productName;
    private BigDecimal productCost;
    private BigDecimal productSellPrice;
    private String status;
    private String reason;
}
