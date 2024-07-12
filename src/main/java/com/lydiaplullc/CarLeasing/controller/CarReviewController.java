package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.CarReview;
import com.lydiaplullc.CarLeasing.request.CarReviewRequest;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.response.CarReviewResponse;
import com.lydiaplullc.CarLeasing.response.CustomerResponse;
import com.lydiaplullc.CarLeasing.service.CarReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
public class CarReviewController {
    private final CarReviewService carReviewService;

    @PostMapping("/add/new-review")
    public ResponseEntity<CarReviewResponse> addCarReview(
            @RequestBody CarReviewRequest carReviewRequest
    ) {
        CarReview savedCarReview = carReviewService.addCarReview(carReviewRequest);
        CarReviewResponse carReviewResponse = getCarReviewResponse(savedCarReview);

        return ResponseEntity.ok(carReviewResponse);
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<CarReviewResponse> updateCarReview(
            @PathVariable Long reviewId,
            @RequestBody CarReviewRequest carReviewRequest
    ) {
        CarReview savedCarReview = carReviewService.updateCarReview(reviewId, carReviewRequest);
        CarReviewResponse carReviewResponse = getCarReviewResponse(savedCarReview);

        return ResponseEntity.ok(carReviewResponse);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<CarReviewResponse> getCarReviewById(
            @PathVariable Long reviewId
    ){
        CarReview carReview = carReviewService.getCarReviewById(reviewId);
        CarReviewResponse carReviewResponse = getCarReviewResponse(carReview);
        
        return ResponseEntity.ok(carReviewResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarReviewResponse>> getAllCarReviews() {
        List<CarReview> carReviews = carReviewService.getCarReviewsAll();

        List<CarReviewResponse> carReviewResponses = new ArrayList<>();
        for(CarReview carReview:carReviews) {
            CarReviewResponse carReviewResponse = getCarReviewResponse(carReview);
            carReviewResponses.add(carReviewResponse);
        }

        return ResponseEntity.ok(carReviewResponses);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteCarReview(
            @PathVariable Long reviewId
    ) {
        carReviewService.deleteCarReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<List<CarReviewResponse>> getReviewsByCustomerId(
            @PathVariable Long customerId
    ) {
        List<CarReview> carReviews = carReviewService.getCarReviewByCustomerId(customerId);

        List<CarReviewResponse> carReviewResponses = new ArrayList<>();
        for(CarReview carReview:carReviews) {
            CarReviewResponse carReviewResponse = getCarReviewResponse(carReview);
            carReviewResponses.add(carReviewResponse);
        }

        return ResponseEntity.ok(carReviewResponses);
    }

    @GetMapping("/by-car-id/{carId}")
    public ResponseEntity<List<CarReviewResponse>> getReviewsByCarId(
            @PathVariable Long carId
    ) {
        List<CarReview> carReviews = carReviewService.getCarReviewByCarId(carId);

        List<CarReviewResponse> carReviewResponses = new ArrayList<>();
        for(CarReview carReview:carReviews) {
            CarReviewResponse carReviewResponse = getCarReviewResponse(carReview);
            carReviewResponses.add(carReviewResponse);
        }

        return ResponseEntity.ok(carReviewResponses);
    }

    public static CarReviewResponse getCarReviewResponse(CarReview carReview) {
        CustomerResponse customerResponse = CustomerController.getCustomerResponse(carReview.getCustomer());
        CarResponse carResponse = CarController.getCarResponse(carReview.getCar());

        return new CarReviewResponse(
                carReview.getId(),
                carReview.getRating(),
                carReview.getComment(),
                carReview.getReviewDate(),
                customerResponse,
                carResponse
        );
    }
}
