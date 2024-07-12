package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.CarRental;
import com.lydiaplullc.CarLeasing.request.CarRentalRequest;
import com.lydiaplullc.CarLeasing.response.CarRentalResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.response.CustomerResponse;
import com.lydiaplullc.CarLeasing.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class CarRentalController {
    private final CarRentalService carRentalService;

    @PostMapping("/add/new-rental")
    public ResponseEntity<CarRentalResponse> addCarRental(
            @RequestBody CarRentalRequest carRentalRequest
    ) {
        CarRental savedCarRental = carRentalService.addCarRental(carRentalRequest);
        CarRentalResponse carRentalResponse = getCarRentalResponse(savedCarRental);
        return ResponseEntity.ok(carRentalResponse);
    }

    @PutMapping("/update/{rentalId}")
        public ResponseEntity<CarRentalResponse> updateCarRental(
            @PathVariable Long rentalId,
            @RequestBody CarRentalRequest carRentalRequest
    ) {
        CarRental savedCarRental = carRentalService.updateCarRental(rentalId, carRentalRequest);
        CarRentalResponse carRentalResponse = getCarRentalResponse(savedCarRental);
        return ResponseEntity.ok(carRentalResponse);
    }

    @GetMapping("/rental/{rentalId}")
    public ResponseEntity<CarRentalResponse> getCarRentalById(
            @PathVariable Long rentalId
    ) {
        CarRental carRental = carRentalService.getCarRentalById(rentalId);
        CarRentalResponse carRentalResponse = getCarRentalResponse(carRental);
        return ResponseEntity.ok(carRentalResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarRentalResponse>> getAllCarRentals() {
        List<CarRental> carRentals = carRentalService.getCarRentalAll();

        List<CarRentalResponse> carRentalResponses = new ArrayList<>();
        for(CarRental carRental: carRentals) {
            CarRentalResponse carRentalResponse = getCarRentalResponse(carRental);
            carRentalResponses.add(carRentalResponse);
        }

        return ResponseEntity.ok(carRentalResponses);
    }

    @DeleteMapping("/delete/{rentalId}")
    public ResponseEntity<Void> deleteCarRental(
            @PathVariable Long rentalId
    ) {
        carRentalService.deleteCarRental(rentalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<List<CarRentalResponse>> getCarRentalsByCustomerId(
            @PathVariable Long customerId
    ) {
        List<CarRental> carRentals = carRentalService.getCarRentalByCustomerId(customerId);

        List<CarRentalResponse> carRentalResponses = new ArrayList<>();
        for(CarRental carRental: carRentals) {
            CarRentalResponse carRentalResponse = getCarRentalResponse(carRental);
            carRentalResponses.add(carRentalResponse);
        }

        return ResponseEntity.ok(carRentalResponses);
    }

    @GetMapping("/by-car-id/{carId}")
    public ResponseEntity<List<CarRentalResponse>> getCarRentalsByCarId(
            @PathVariable Long carId
    ) {
        List<CarRental> carRentals = carRentalService.getCarRentalByCarId(carId);

        List<CarRentalResponse> carRentalResponses = new ArrayList<>();
        for(CarRental carRental: carRentals) {
            CarRentalResponse carRentalResponse = getCarRentalResponse(carRental);
            carRentalResponses.add(carRentalResponse);
        }

        return ResponseEntity.ok(carRentalResponses);
    }

    public static CarRentalResponse getCarRentalResponse(CarRental carRental) {
        CustomerResponse customerResponse = CustomerController.getCustomerResponse(carRental.getCustomer());
        CarResponse carResponse = CarController.getCarResponse(carRental.getCar());

        return new CarRentalResponse(
                carRental.getId(),
                carRental.getRentalDate(),
                carRental.getReturnDate(),
                customerResponse,
                carResponse
        );
    }
}
