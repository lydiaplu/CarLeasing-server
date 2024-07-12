package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.request.CustomerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICustomerService {
    Customer addCustomer(CustomerRequest customerRequest);

    Customer updateCustomer(Long id, CustomerRequest customerRequest);

    Customer addCustomerDriverLicenseFrontAndEndPhoto(Long id, MultipartFile driverLicenseFrontPhoto, MultipartFile driverLicenseBackPhoto);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    void deleteCustomer(Long id);
}
