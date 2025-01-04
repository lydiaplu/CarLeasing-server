package com.lydiaplullc.CarLeasing.request;

import com.lydiaplullc.CarLeasing.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedCarRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private RentalStatus status;
    private Long customerId;
    private Long carId;
}
