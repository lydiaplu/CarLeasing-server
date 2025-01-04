package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarReviewRepository extends JpaRepository<CarReview, Long> {
    @Query("SELECT cr FROM CarReview cr WHERE cr.customer.id = :customerId")
    List<CarReview> findCarReviewsByCustomerId(Long customerId);

    @Query("SELECT cr FROM CarReview cr WHERE cr.car.id = :carId")
    List<CarReview> findCarReviewByCarId(Long carId);

    @Query("SELECT cr FROM CarReview cr WHERE cr.rentedCar.id = :rentedId")
    List<CarReview> findCarReviewByRentedId(Long rentedId);
}
