package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarReview;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.model.RentedCar;
import com.lydiaplullc.CarLeasing.repository.CarReviewRepository;
import com.lydiaplullc.CarLeasing.request.CarReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarReviewService implements ICarReviewService{
    private final CarReviewRepository carReviewRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final RentedCarService rentedCarService;

    @Override
    public CarReview addCarReview(CarReviewRequest carReviewRequest) {
        CarReview carReview = new CarReview();
        setCarReviewModel(carReview, carReviewRequest);
        return carReviewRepository.save(carReview);
    }

    @Override
    public CarReview updateCarReview(Long id, CarReviewRequest carReviewRequest) {
        CarReview carReview = getCarReviewById(id);
        setCarReviewModel(carReview, carReviewRequest);
        return carReviewRepository.save(carReview);
    }

    @Override
    public CarReview getCarReviewById(Long id) {
        return carReviewRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Car Review not found with ID: " + id));
    }

    @Override
    public List<CarReview> getCarReviewsAll(){
        return carReviewRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deleteCarReview(Long id) {
        getCarReviewById(id);
        carReviewRepository.deleteById(id);
    }

    @Override
    public List<CarReview> getCarReviewByCustomerId(Long customerId) {
        return carReviewRepository.findCarReviewsByCustomerId(customerId);
    }

    @Override
    public List<CarReview> getCarReviewByCarId(Long carId) {
        return carReviewRepository.findCarReviewByCarId(carId);
    }

    @Override
    public List<CarReview> getCarReviewByRentedId(Long rentedId) {
        return carReviewRepository.findCarReviewByCarId(rentedId);
    }

    private void setCarReviewModel(CarReview carReview, CarReviewRequest carReviewRequest) {
        Customer customer = customerService.getCustomerById(carReviewRequest.getCustomerId());
        Car car = carService.getCarById(carReviewRequest.getCarId()).get();
        RentedCar rentedCar = rentedCarService.getRentedCarById(carReviewRequest.getRentedId());

        carReview.setRating(carReviewRequest.getRating());
        carReview.setComment(carReview.getComment());
        carReview.setReviewDate(carReviewRequest.getReviewDate());
        carReview.setCustomer(customer);
        carReview.setCar(car);
        carReview.setRentedCar(rentedCar);
    }
}
