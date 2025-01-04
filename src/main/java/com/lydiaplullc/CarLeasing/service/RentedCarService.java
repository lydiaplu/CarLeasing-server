package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.model.RentedCar;
import com.lydiaplullc.CarLeasing.repository.RentedCarRepository;
import com.lydiaplullc.CarLeasing.request.RentedCarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentedCarService implements IRentedCarService {
    private final RentedCarRepository rentedCarRepository;
    private final CarService carService;
    private final CustomerService customerService;

    @Override
    public RentedCar addRentedCar(RentedCarRequest rentedCarRequest) {
        RentedCar rentedCar = new RentedCar();
        setRentedCarModel(rentedCar, rentedCarRequest);
        return rentedCarRepository.save(rentedCar);
    }

    @Override
    public RentedCar updateRentedCar(Long id, RentedCarRequest rentedCarRequest){
        RentedCar rentedCar = getRentedCarById(id);
        setRentedCarModel(rentedCar, rentedCarRequest);
        return rentedCarRepository.save(rentedCar);
    }

    @Override
    public RentedCar getRentedCarById(Long id) {
        return rentedCarRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Rented car not found with ID: " + id));
    }

    @Override
    public List<RentedCar> getRentedCarAll() {
        return rentedCarRepository.findAll();
    }

    @Override
    public void deleteRentedCar(Long id){
        getRentedCarById(id);
        rentedCarRepository.deleteById(id);
    }

    @Override
    public List<RentedCar> getRentedCarByCustomerId(Long customerId) {
        return rentedCarRepository.findRentedCarsByCustomerId(customerId);
    }

    @Override
    public List<RentedCar> getRentedCarByCarId(Long carId) {
        return rentedCarRepository.findRentedCarsByCarId(carId);
    }

    private void setRentedCarModel(RentedCar rentedCar, RentedCarRequest rentedCarRequest) {
        Customer customer = customerService.getCustomerById(rentedCarRequest.getCustomerId());
        Car car = carService.getCarById(rentedCarRequest.getCarId()).get();

        rentedCar.setStartDate(rentedCarRequest.getStartDate());
        rentedCar.setEndDate(rentedCarRequest.getEndDate());
        rentedCar.setStatus(rentedCarRequest.getStatus());
        rentedCar.setCustomer(customer);
        rentedCar.setCar(car);
    }
}
