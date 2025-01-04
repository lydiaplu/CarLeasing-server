package com.lydiaplullc.CarLeasing.response;

import com.lydiaplullc.CarLeasing.enums.PaymentStatus;
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
    private String paymentStatus;
    private CustomerResponse customer;
    private RentedCarResponse rentedCar;

    public PaymentResponse(Long id, BigDecimal amount, LocalDate paymentDate, String paymentMethod, PaymentStatus paymentStatus, CustomerResponse customer, RentedCarResponse rentedCarResponse) {
        this.id = String.valueOf(id);
        this.amount = String.valueOf(amount);
        this.paymentDate = paymentDate != null ? String.valueOf(paymentDate) : "";
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus != null ? String.valueOf(paymentStatus) : "";
        this.customer = customer;
        this.rentedCar = rentedCarResponse;
    }
}
