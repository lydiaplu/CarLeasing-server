package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarReviewResponse {
    private String id;
    private String rating;
    private String comment;
    private String reviewDate;
    private CustomerResponse customer;
    private CarResponse car;
    private RentedCarResponse rentedCar;

    public CarReviewResponse(Long id, int rating, String comment, LocalDate reviewDate, CustomerResponse customer, CarResponse car, RentedCarResponse rentedCar) {
        this.id = String.valueOf(id);
        this.rating = String.valueOf(rating);
        this.comment = comment;
        this.reviewDate = reviewDate != null ? String.valueOf(reviewDate) : "";
        this.customer = customer;
        this.car = car;
        this.rentedCar = rentedCar;
    }
}
