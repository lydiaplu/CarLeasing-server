package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String id;
    private String amount;
    private String paymentDate;
    private String paymentMethod;
    private CustomerResponse customer;
    private CarRentalResponse carRental;

    public PaymentResponse(Long id, BigDecimal amount, LocalDate paymentDate, String paymentMethod, CustomerResponse customer, CarRentalResponse carRental) {
        this.id = String.valueOf(id);
        this.amount = String.valueOf(amount);
        this.paymentDate = paymentDate != null ? String.valueOf(paymentDate) : "";
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.carRental = carRental;
    }
}
