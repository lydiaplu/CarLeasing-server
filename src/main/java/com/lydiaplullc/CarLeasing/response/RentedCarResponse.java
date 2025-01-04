package com.lydiaplullc.CarLeasing.response;

import com.lydiaplullc.CarLeasing.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedCarResponse {
    private String id;
    private String startDate;
    private String endDate;
    private String status;
    private CustomerResponse customer;
    private CarResponse car;
    // private PaymentResponse payment;
    // private CarReviewResponse carReview;

    public RentedCarResponse(Long id,
                             LocalDate startDate,
                             LocalDate endDate,
                             RentalStatus status,
                             CustomerResponse customerResponse,
                             CarResponse carResponse
    ) {
        this.id = String.valueOf(id);
        this.startDate = startDate != null ? String.valueOf(startDate) : "";
        this.endDate = endDate != null ? String.valueOf(endDate) : "";
        this.status = status != null ? String.valueOf(status) : "";
        this.customer = customerResponse;
        this.car = carResponse;
        // this.payment = paymentResponse;
        // this.carReview = carReviewResponse;
    }
}
