package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {
    @Query("SELECT cr FROM CarRental cr WHERE cr.customer.id = :customerId")
    List<CarRental> findCarRentalsByCustomerId(Long customerId);

    @Query("SELECT cr FROM CarRental cr WHERE cr.car.id = :carId")
    List<CarRental> findCarRentalsByCarId(Long carId);
}
