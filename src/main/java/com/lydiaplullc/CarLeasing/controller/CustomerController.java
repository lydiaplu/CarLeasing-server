package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.request.CustomerRequest;
import com.lydiaplullc.CarLeasing.response.CustomerResponse;
import com.lydiaplullc.CarLeasing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add/new-customer")
    public ResponseEntity<CustomerResponse> addCustomer(
            @RequestBody CustomerRequest customerRequest
    ) {
        Customer savedCustomer = customerService.addCustomer(customerRequest);
        CustomerResponse customerResponse = getCustomerResponse(savedCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerRequest customerRequest
    ) {
        Customer savedCustomer = customerService.updateCustomer(customerId, customerRequest);
        CustomerResponse customerResponse = getCustomerResponse(savedCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/upload/driver-license-photos/{customerId}")
    public ResponseEntity<CustomerResponse> addCustomerDriverLicenseFrontAndEndPhoto(
            @PathVariable Long customerId,
            @RequestParam MultipartFile driverLicenseFrontPhoto,
            @RequestParam MultipartFile driverLicenseBackPhoto
    ) {
        Customer savedCustomer = customerService.addCustomerDriverLicenseFrontAndEndPhoto(customerId, driverLicenseFrontPhoto, driverLicenseBackPhoto);
        CustomerResponse customerResponse = getCustomerResponse(savedCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long customerId
    ) {
        Customer customer = customerService.getCustomerById(customerId);
        CustomerResponse customerResponse = getCustomerResponse(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer: customers) {
            CustomerResponse customerResponse = getCustomerResponse(customer);
            customerResponses.add(customerResponse);
        }

        return ResponseEntity.ok(customerResponses);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId
    ) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static CustomerResponse getCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getMiddleName(),
                customer.getGender(),
                customer.getDateOfBirth(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getDriverLicenseNumber(),
                customer.getDriverLicenseFrontPhoto(),
                customer.getDriverLicenseBackPhoto(),
                customer.getCreditScore(),
                customer.getDrivingYears(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getCountry(),
                customer.getPostalCode(),
                customer.getEmergencyContactPhone(),
                customer.getIsDisabled(),
                customer.getDisabilityDescription(),
                customer.getRegistrationDate()
        );
    }
}
