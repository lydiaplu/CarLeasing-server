package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.DataValidationException;
import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.repository.CustomerRepository;
import com.lydiaplullc.CarLeasing.request.CustomerRequest;
import com.lydiaplullc.CarLeasing.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer registerCustomer(String email, String password) {
        getCustomerByEmail(email).ifPresent(existingCustomer -> {
            throw new ResourceNotFoundException("Email already exists: " + email);
        });

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(PasswordUtil.hashPassword(password));
        customer.setRegistrationDate(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Customer validateLogin(String email, String password) {
        Customer customer = getCustomerByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with Email: " + email));

        String storedPassword = customer.getPassword();
        if (PasswordUtil.checkPassword(password, storedPassword)) {
            return customer;
        }

        return null;
    }

    @Override
    public Customer addCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        setCustomerModel(customer, customerRequest);

        // set and encrypted password
        if (customerRequest.getPassword() != null && !customerRequest.getPassword().isEmpty()) {
            customer.setPassword(PasswordUtil.hashPassword(customerRequest.getPassword()));
        }
        customer.setRegistrationDate(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = getCustomerById(id);
        setCustomerModel(customer, customerRequest);
        return customerRepository.save(customer);
    }

    @Override
    public Customer addCustomerDriverLicenseFrontAndEndPhoto(Long id, MultipartFile driverLicenseFrontPhoto, MultipartFile driverLicenseBackPhoto) {
        Customer customer = getCustomerById(id);

        try {
//            if (driverLicenseFrontPhoto.isEmpty() || driverLicenseBackPhoto.isEmpty()) {
//                throw new DataValidationException("Both driver license front and back photos are required.");
//            }

            if (driverLicenseFrontPhoto != null) {
                byte[] frontPhotoBytes = driverLicenseFrontPhoto.getBytes();
                Blob frontPhotoBlob = new SerialBlob(frontPhotoBytes);
                customer.setDriverLicenseFrontPhoto(frontPhotoBlob);
            }

            if (driverLicenseBackPhoto != null) {
                byte[] backPhotoBytes = driverLicenseBackPhoto.getBytes();
                Blob backPhotoBlob = new SerialBlob(backPhotoBytes);
                customer.setDriverLicenseBackPhoto(backPhotoBlob);
            }

            return customerRepository.save(customer);

        } catch (IOException e) {
            throw new DataValidationException("Error processing photo data: " + e.getMessage());
        } catch (SQLException e) {
            throw new DataValidationException("Error creating BLOB from photo data: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> getByDriverLicenseNumber(String driverLicenseNumber) {
        return customerRepository.findByDriverLicenseNumber(driverLicenseNumber);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deleteCustomer(Long id) {
        // Ensure the customer exists to provide a clear error message if not
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    private void setCustomerModel(Customer customer, CustomerRequest customerRequest) {
        // Because I separate info and file save to database
        // so there is not driverLicenseFrontPhoto and driverLicenseBackPhoto

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setGender(customerRequest.getGender());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setPhone(customerRequest.getPhone());
        customer.setEmail(customerRequest.getEmail());
        customer.setDriverLicenseNumber(customerRequest.getDriverLicenseNumber());
        customer.setCreditScore(customerRequest.getCreditScore());
        customer.setDrivingYears(customerRequest.getDrivingYears());
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setState(customerRequest.getState());
        customer.setCountry(customerRequest.getCountry());
        customer.setPostalCode(customerRequest.getPostalCode());
        customer.setEmergencyContactPhone(customerRequest.getEmergencyContactPhone());
        customer.setIsDisabled(customerRequest.getIsDisabled());
        customer.setDisabilityDescription(customerRequest.getDisabilityDescription());
    }
}
