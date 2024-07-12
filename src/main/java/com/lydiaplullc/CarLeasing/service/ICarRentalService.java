package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarRental;
import com.lydiaplullc.CarLeasing.request.CarRentalRequest;

import java.util.List;

public interface ICarRentalService {
    CarRental addCarRental(CarRentalRequest carRentalRequest);

    CarRental updateCarRental(Long id, CarRentalRequest carRentalRequest);

    CarRental getCarRentalById(Long id);

    List<CarRental> getCarRentalAll();

    public void deleteCarRental(Long id);

    List<CarRental> getCarRentalByCustomerId(Long customerId);

    List<CarRental> getCarRentalByCarId(Long carId);
}
