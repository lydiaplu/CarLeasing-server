package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.request.CarRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarService {
    Car addCar(CarRequest carRequest) throws SQLException;

    Car updateCar(Long carId, CarRequest carRequest) throws SQLException;

    Optional<Car> getCarById(Long carId);

    Optional<Car> getCarByIdAndFetchPictures(Long carId);

    Optional<List<Car>> getCarByCarTypeId(Long carTypeId);

    List<Car> getAllCar();

    List<Car> getAllCarByGroup();

    List<Car> getDistinctByEveryType();

    List<Car> getCarByCheckInOutDataAndFuletypeBrandModelType(String checkInDate, String checkOutDate, String fuelType, String carBrand, String model, String carType);

    List<Car> getCarByTypeAndBrand(List<Long> carBrand, List<Long> carType);

    void deleteCar(Long carId);

    List<String> getAllLicensePlate();

    Optional<Car> getCarByLicensePlate(Long carLicensePlate);

    List<String> getAllModels();

    List<String> getAllAvailable();

    List<String> getAllFuelType();
}
