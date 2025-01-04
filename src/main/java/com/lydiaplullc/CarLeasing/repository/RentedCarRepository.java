package com.lydiaplullc.CarLeasing.repository;


import com.lydiaplullc.CarLeasing.model.RentedCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentedCarRepository extends JpaRepository<RentedCar,Long> {
    @Query("SELECT rc FROM RentedCar rc WHERE rc.customer.id = :customerId")
    List<RentedCar> findRentedCarsByCustomerId(Long customerId);

    @Query("SELECT rc FROM RentedCar rc WHERE rc.car.id = :carId")
    List<RentedCar> findRentedCarsByCarId(Long carId);
}
