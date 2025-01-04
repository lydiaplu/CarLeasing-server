package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarReview;
import com.lydiaplullc.CarLeasing.request.CarReviewRequest;

import java.util.List;

public interface ICarReviewService {
    CarReview addCarReview(CarReviewRequest carReviewRequest);

    CarReview updateCarReview(Long id, CarReviewRequest carReviewRequest);

    CarReview getCarReviewById(Long id);

    List<CarReview> getCarReviewsAll();

    void deleteCarReview(Long id);

    List<CarReview> getCarReviewByCustomerId(Long customerId);

    List<CarReview> getCarReviewByCarId(Long carId);

    List<CarReview> getCarReviewByRentedId(Long rentedId);
}
