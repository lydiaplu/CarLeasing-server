package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.driverLicenseNumber = :driverLicenseNumber")
    Optional<Customer> findByDriverLicenseNumber(String driverLicenseNumber);

}
