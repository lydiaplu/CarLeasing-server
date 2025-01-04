package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarPicture;
import com.lydiaplullc.CarLeasing.request.CarRequest;
import com.lydiaplullc.CarLeasing.response.CarBrandResponse;
import com.lydiaplullc.CarLeasing.response.CarPictureResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.response.CarTypeResponse;
import com.lydiaplullc.CarLeasing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/add/new-car")
    public ResponseEntity<CarResponse> addCar(
            @RequestBody CarRequest carRequest
    ) throws SQLException {
        Car savedCar = carService.addCar(carRequest);
        CarResponse carResponse = getCarResponse(savedCar);
        return ResponseEntity.ok(carResponse);
    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Long carId,
            @RequestBody CarRequest carRequest
    ) throws SQLException {
        Car savedCar = carService.updateCar(carId, carRequest);
        CarResponse carResponse = getCarResponse(savedCar);
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<Optional<CarResponse>> getCarById(@PathVariable Long carId) {
        Optional<Car> theCar = carService.getCarById(carId);
        return theCar.map(car -> {
            CarResponse carResponse = getCarResponse(car);
            return ResponseEntity.ok(Optional.of(carResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("car not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponse>> getAllCars() throws SQLException {
        List<Car> cars = carService.getAllCar();

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/all-by-group")
    public ResponseEntity<List<CarResponse>> getAllCarsByGroup() throws SQLException {
        List<Car> cars = carService.getAllCarByGroup();

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/distinct-by-every-type")
    public ResponseEntity<List<CarResponse>> getDistinctByEveryType() throws SQLException {
        List<Car> cars = carService.getDistinctByEveryType();

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/all-by-checkinout-fueltype-brand-model-type")
    public ResponseEntity<List<CarResponse>> getCarByCheckInOutDataAndFuletypeBrandModelType(
            @RequestParam(required = false) String checkInDate,
            @RequestParam(required = false) String checkOutDate,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String carBrand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String carType
    ) {
        List<Car> cars = carService.getCarByCheckInOutDataAndFuletypeBrandModelType(checkInDate, checkOutDate, fuelType, carBrand, model, carType);

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/all-by-type-brand")
    public ResponseEntity<List<CarResponse>> getCarByTypeAndBrand(
            @RequestParam(required = false) String carBrand,
            @RequestParam(required = false) String carType
    ) {
        System.out.println("carBrand" + carBrand);
        System.out.println("carType" + carType);

        // the data from front-end API is string, so have to change it to Integer
        List<Long> carBrandList = carBrand != null && !carBrand.isEmpty()
                ? Arrays.stream(carBrand.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList())
                : null;

        List<Long> carTypeList = carType !=null && !carType.isEmpty()
                ? Arrays.stream(carType.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList())
                : null;

        List<Car> cars = carService.getCarByTypeAndBrand(carBrandList, carTypeList);

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/popular-rented")
    public ResponseEntity<Page<CarResponse>> getCarByPopularRented(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> cars =carService.getCarByPopularRented(pageable);

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        Page<CarResponse> carResponsesPage = new PageImpl<>(carResponses, pageable, cars.getTotalElements());
        return ResponseEntity.ok(carResponsesPage);
    }

    @GetMapping("/newest-cars")
    public ResponseEntity<Page<CarResponse>> getNewestCar(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> cars =carService.getNewestCar(pageable);

        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        Page<CarResponse> carResponsesPage = new PageImpl<>(carResponses, pageable, cars.getTotalElements());
        return ResponseEntity.ok(carResponsesPage);
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/licenseplate/all")
    public List<String> getAllLicensePlate() {
        return carService.getAllLicensePlate();
    }

    @GetMapping("/car/licenseplate/{carLicensePlate}")
    public ResponseEntity<Optional<CarResponse>> getCarByLicensePlate(@PathVariable Long carLicensePlate) {
        Optional<Car> theCar = carService.getCarByLicensePlate(carLicensePlate);
        return theCar.map(car -> {
            CarResponse carResponse = getCarResponse(car);
            return ResponseEntity.ok(Optional.of(carResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("car not found"));
    }

    @GetMapping("/all-models")
    public List<String> getAllModels() {
        return carService.getAllModels();
    }

    @GetMapping("/all-available")
    public List<String> getAllAvailable() {
        return carService.getAllAvailable();
    }

    @GetMapping("/all-fueltype")
    public List<String> getAllFuelType() {
        return carService.getAllFuelType();
    }

    public static CarResponse getCarResponse(Car car) {
        CarBrandResponse carBrandResponse = CarBrandController.getCarBrandResponse(car.getCarBrand());
        CarTypeResponse carTypeResponse = CarTypeController.getCarTypeResponse(car.getCarType());

        List<CarPictureResponse> carPictureResponses = new ArrayList<>();
        for (CarPicture carPicture : car.getPictures()) {
            CarPictureResponse carPictureResponse = CarPictureController.getCarPictureResponse(carPicture, null);
            carPictureResponses.add(carPictureResponse);
        }

        return new CarResponse(
                car.getId(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getLicensePlate(),
                car.getSeats(),
                car.getMileage(),
                car.getFuelType(),
                car.getPrice(),
                car.getAWD(),
                car.getLeatherTrimmedUpholstery(),
                car.getMoonroof(),
                car.getRAB(),
                car.getBlindSpotAssist(),
                car.getKeylessEntrySystem(),
                car.getEngineDisplacement(),
                car.getAvailable(),
                car.getDescription(),
                carTypeResponse,
                carBrandResponse,
                carPictureResponses
        );
    }
}
