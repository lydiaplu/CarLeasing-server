package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.request.CustomerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer registerCustomer(String email, String password);

    Customer validateLogin(String email, String password);

    Customer addCustomer(CustomerRequest customerRequest);

    Customer updateCustomer(Long id, CustomerRequest customerRequest);

    Customer addCustomerDriverLicenseFrontAndEndPhoto(Long id, MultipartFile driverLicenseFrontPhoto, MultipartFile driverLicenseBackPhoto);

    Customer getCustomerById(Long id);

    Optional<Customer> getCustomerByEmail(String email);

    Optional<Customer> getByDriverLicenseNumber(String driverLicenseNumber);

    List<Customer> getAllCustomers();

    void deleteCustomer(Long id);
}
