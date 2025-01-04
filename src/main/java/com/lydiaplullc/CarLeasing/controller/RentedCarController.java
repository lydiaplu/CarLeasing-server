package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.RentedCar;
import com.lydiaplullc.CarLeasing.request.RentedCarRequest;
import com.lydiaplullc.CarLeasing.response.*;
import com.lydiaplullc.CarLeasing.service.RentedCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentedCarController {
    private final RentedCarService rentedCarService;

    @PostMapping("/add/new-rental")
    public ResponseEntity<RentedCarResponse> addRentedCar(
            @RequestBody RentedCarRequest rentedCarRequest
    ) {
        RentedCar savedRentedCar = rentedCarService.addRentedCar(rentedCarRequest);
        RentedCarResponse rentedCarResponse = getRentedCarResponse(savedRentedCar);
        return ResponseEntity.ok(rentedCarResponse);
    }

    @PutMapping("/update/{rentedId}")
    public ResponseEntity<RentedCarResponse> updateRentedCar(
            @PathVariable Long rentedId,
            @RequestBody RentedCarRequest rentedCarRequest
    ) {
        RentedCar savedRentedCar = rentedCarService.updateRentedCar(rentedId, rentedCarRequest);
        RentedCarResponse rentedCarResponse = getRentedCarResponse(savedRentedCar);
        return ResponseEntity.ok(rentedCarResponse);
    }

    @GetMapping("/rental/{rentedId}")
    public ResponseEntity<RentedCarResponse> getRentedCarById(
            @PathVariable Long rentedId
    ) {
        RentedCar rentedCar = rentedCarService.getRentedCarById(rentedId);
        RentedCarResponse rentedCarResponse = getRentedCarResponse(rentedCar);
        return ResponseEntity.ok(rentedCarResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RentedCarResponse>> getAllRentedCars() {
        List<RentedCar> rentedCars = rentedCarService.getRentedCarAll();

        List<RentedCarResponse> rentedCarResponses = new ArrayList<>();
        for(RentedCar rentedCar: rentedCars) {
            RentedCarResponse rentedCarResponse = getRentedCarResponse(rentedCar);
            rentedCarResponses.add(rentedCarResponse);
        }

        return ResponseEntity.ok(rentedCarResponses);
    }

    @DeleteMapping("/delete/{rentedId}")
    public ResponseEntity<Void> deleteRentedCar(
            @PathVariable Long rentedId
    ) {
        rentedCarService.deleteRentedCar(rentedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<List<RentedCarResponse>> getRentedCarsByCustomerId(
            @PathVariable Long customerId
    ) {
        List<RentedCar> rentedCars = rentedCarService.getRentedCarByCustomerId(customerId);

        List<RentedCarResponse> rentedCarResponses = new ArrayList<>();
        for(RentedCar rentedCar: rentedCars) {
            RentedCarResponse rentedCarResponse = getRentedCarResponse(rentedCar);
            rentedCarResponses.add(rentedCarResponse);
        }

        return ResponseEntity.ok(rentedCarResponses);
    }

    @GetMapping("/by-car-id/{carId}")
    public ResponseEntity<List<RentedCarResponse>> getRentedCarsByCarId(
            @PathVariable Long carId
    ) {
        List<RentedCar> rentedCars = rentedCarService.getRentedCarByCarId(carId);

        List<RentedCarResponse> rentedCarResponses = new ArrayList<>();
        for(RentedCar rentedCar: rentedCars) {
            RentedCarResponse rentedCarResponse = getRentedCarResponse(rentedCar);
            rentedCarResponses.add(rentedCarResponse);
        }

        return ResponseEntity.ok(rentedCarResponses);
    }

    public static RentedCarResponse getRentedCarResponse(RentedCar rentedCar) {
        CustomerResponse customerResponse = CustomerController.getCustomerResponse(rentedCar.getCustomer());
        CarResponse carResponse = CarController.getCarResponse(rentedCar.getCar());
        // 这里在获取Response时，出现了循环嵌套
        // PaymentResponse paymentResponse = PaymentController.getPaymentResponse(rentedCar.getPayment());
        // CarReviewResponse carReviewResponse = CarReviewController.getCarReviewResponse(rentedCar.getCarReview());

        return new RentedCarResponse(
                rentedCar.getId(),
                rentedCar.getStartDate(),
                rentedCar.getEndDate(),
                rentedCar.getStatus(),
                customerResponse,
                carResponse
                // paymentResponse,
                // carReviewResponse
        );
    }
}
