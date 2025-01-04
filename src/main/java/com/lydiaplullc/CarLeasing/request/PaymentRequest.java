package com.lydiaplullc.CarLeasing.request;

import com.lydiaplullc.CarLeasing.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private Long customerId;
    private Long rentedId;
}
