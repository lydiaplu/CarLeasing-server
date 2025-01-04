package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.RentedCar;
import com.lydiaplullc.CarLeasing.request.RentedCarRequest;

import java.util.List;

public interface IRentedCarService {
    RentedCar addRentedCar(RentedCarRequest rentedCarRequest);

    RentedCar updateRentedCar(Long id, RentedCarRequest rentedCarRequest);

    RentedCar getRentedCarById(Long id);

    List<RentedCar> getRentedCarAll();

    public void deleteRentedCar(Long id);

    List<RentedCar> getRentedCarByCustomerId(Long customerId);

    List<RentedCar> getRentedCarByCarId(Long carId);
}
