package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.request.CustomerRequest;
import com.lydiaplullc.CarLeasing.response.CustomerResponse;
import com.lydiaplullc.CarLeasing.response.LoginCustomerResponse;
import com.lydiaplullc.CarLeasing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            Customer savedCustomer = customerService.registerCustomer(email, password);
            LoginCustomerResponse loginCustomerResponse = new LoginCustomerResponse(
                    savedCustomer.getId(),
                    savedCustomer.getEmail()
            );
            return ResponseEntity.ok(loginCustomerResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The email already exist");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            Customer customer = customerService.validateLogin(email, password);
            if (customer != null) {
                CustomerResponse customerResponse = getCustomerResponse(customer);
                return ResponseEntity.ok(customerResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The email or password does not match");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The email not exist");
        }

    }

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
            @RequestParam(required = false) MultipartFile driverLicenseFrontPhoto,
            @RequestParam(required = false) MultipartFile driverLicenseBackPhoto
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

    @GetMapping("/customer-email/{email}")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(
            @PathVariable String email
    ) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        if (customer.isPresent()) {
            CustomerResponse customerResponse = getCustomerResponse(customer.get());
            return ResponseEntity.ok(customerResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/customer-driverlicensenumber/{driverLicenseNumber}")
    public ResponseEntity<CustomerResponse> getByDriverLicenseNumber(
            @PathVariable String driverLicenseNumber
    ) {
        Optional<Customer> customer = customerService.getByDriverLicenseNumber(driverLicenseNumber);

        if (customer.isPresent()) {
            CustomerResponse customerResponse = getCustomerResponse(customer.get());
            return ResponseEntity.ok(customerResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
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
