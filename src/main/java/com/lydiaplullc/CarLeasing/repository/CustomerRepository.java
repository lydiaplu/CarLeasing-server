package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
