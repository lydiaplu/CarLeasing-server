package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT DISTINCT c.model FROM Car c")
    List<String> findDistinctModels();

    @Query("SELECT DISTINCT c.available FROM Car c")
    List<String> findDistinctAvailable();

    @Query("SELECT DISTINCT c.fuelType FROM Car c")
    List<String> findDistinctFuelType();

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.pictures WHERE c.id = :id")
    Car findByIdAndFetchPictures(Long id);

    @Query("SELECT c FROM Car c WHERE c.carType.id = :carTypeId")
    List<Car> findCarByCarTypeId(Long carTypeId);

    @Query("SELECT c.licensePlate FROM Car c")
    List<String> findAllLicensePlate();

    @Query("SELECT c FROM Car c WHERE c.licensePlate = :carLicensePlate")
    Car findByLicensePlate(Long carLicensePlate);

    @Query("SELECT c FROM Car c ORDER BY c.carBrand.id, c.model, c.carType.id, c.year, c.color, c.licensePlate")
    List<Car> findAllCarByGroup();

    @Query("SELECT c FROM Car c WHERE c.id IN (SELECT MIN(c2.id) FROM Car c2 GROUP BY c2.carBrand.id, c2.model, c2.carType.id)")
    List<Car> findDistinctByEveryType();

    @Query("SELECT c FROM Car c WHERE " +
            "(:fuelType IS NULL OR :fuelType = '' OR c.fuelType = :fuelType) AND " +
            "(:carBrand IS NULL OR :carBrand = '' OR c.carBrand.id = :carBrand) AND " +
            "(:model IS NULL OR :model = '' OR c.model = :model) AND " +
            "(:carType IS NULL OR :carType = '' OR c.carType.id = :carType) AND " +
            "c.available = true AND " +
            "NOT EXISTS (SELECT r FROM CarRental r WHERE r.car.id = c.id AND " +
            "r.rentalDate <= :checkOutDate AND r.returnDate >= :checkInDate)")
    List<Car> findCarByCheckInOutDataAndFuletype_Brand_Model_Type(String checkInDate, String checkOutDate, String fuelType, String carBrand, String model, String carType);
}
