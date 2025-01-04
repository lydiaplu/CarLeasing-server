package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT py FROM Payment py WHERE py.customer.id = :customerId")
    List<Payment> findPaymentByCustomerId(Long customerId);

    @Query("SELECT py FROM Payment py WHERE py.rentedCar.id = :rentedId")
    List<Payment> findPaymentByRentedId(Long rentedId);
}
