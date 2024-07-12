package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarRental;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.repository.CarRentalRepository;
import com.lydiaplullc.CarLeasing.request.CarRentalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentalService implements ICarRentalService {
    private final CarRentalRepository carRentalRepository;
    private final CarService carService;
    private final CustomerService customerService;

    @Override
    public CarRental addCarRental(CarRentalRequest carRentalRequest) {
        CarRental carRental = new CarRental();
        setCarRentalModel(carRental, carRentalRequest);
        return carRentalRepository.save(carRental);
    }

    @Override
    public CarRental updateCarRental(Long id, CarRentalRequest carRentalRequest){
        CarRental carRental = getCarRentalById(id);
        setCarRentalModel(carRental, carRentalRequest);
        return carRentalRepository.save(carRental);
    }

    @Override
    public CarRental getCarRentalById(Long id) {
        return carRentalRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Car Rental not found with ID: " + id));
    }

    @Override
    public List<CarRental> getCarRentalAll() {
        return carRentalRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deleteCarRental(Long id){
        getCarRentalById(id);
        carRentalRepository.deleteById(id);
    }

    @Override
    public List<CarRental> getCarRentalByCustomerId(Long customerId) {
        return carRentalRepository.findCarRentalsByCustomerId(customerId);
    }

    @Override
    public List<CarRental> getCarRentalByCarId(Long carId) {
        return carRentalRepository.findCarRentalsByCarId(carId);
    }

    private void setCarRentalModel(CarRental carRental, CarRentalRequest carRentalRequest) {
        Customer customer = customerService.getCustomerById(carRentalRequest.getCustomerId());
        Car car = carService.getCarById(carRentalRequest.getCarId()).get();

        carRental.setRentalDate(carRentalRequest.getRentalDate());
        carRental.setReturnDate(carRentalRequest.getReturnDate());
        carRental.setCustomer(customer);
        carRental.setCar(car);
    }
}
