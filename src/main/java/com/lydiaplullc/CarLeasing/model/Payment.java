package com.lydiaplullc.CarLeasing.model;

import com.lydiaplullc.CarLeasing.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private LocalDate paymentDate;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="rented_id")
    private RentedCar rentedCar;

    public Payment() {

    }
}
