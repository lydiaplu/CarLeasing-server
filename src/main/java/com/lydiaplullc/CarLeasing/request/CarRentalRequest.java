package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalRequest {
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private Long customerId;
    private Long carId;
}
