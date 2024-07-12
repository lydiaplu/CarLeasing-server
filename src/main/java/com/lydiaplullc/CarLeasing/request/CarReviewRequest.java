package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarReviewRequest {
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private Long customerId;
    private Long carId;
}
