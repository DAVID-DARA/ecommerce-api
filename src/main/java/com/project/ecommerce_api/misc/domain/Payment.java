package com.project.ecommerce_api.misc.domain;

import com.project.ecommerce_api.order.domain.Order;
import com.project.ecommerce_api.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String provider; // e.g., "paystack", "flutterwave"

    @Column(name = "payment_ref", nullable = false, unique = true)
    private String paymentRef;

    @Column(name = "amount_paid", nullable = false)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
