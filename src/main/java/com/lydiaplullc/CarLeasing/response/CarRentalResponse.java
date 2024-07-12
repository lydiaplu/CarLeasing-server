package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalResponse {
    private String id;
    private String rentalDate;
    private String returnDate;
    private CustomerResponse customer;
    private CarResponse car;

    public CarRentalResponse(Long id, LocalDate rentalDate, LocalDate returnDate, CustomerResponse customer, CarResponse car){
        this.id = String.valueOf(id);
        this.rentalDate = rentalDate!=null ? String.valueOf(rentalDate) : "";
        this.returnDate = returnDate!=null ? String.valueOf(returnDate) : "";
        this.customer = customer;
        this.car = car;
    }
}
