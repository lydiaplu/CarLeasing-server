package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Payment;
import com.lydiaplullc.CarLeasing.request.PaymentRequest;

import java.util.List;

public interface IPaymentService {
    Payment addPayment(PaymentRequest paymentRequest);

    Payment updatePayment(Long id, PaymentRequest paymentRequest);

    Payment getPaymentById(Long id);

    List<Payment> getPaymentAll();

    void deletePayment(Long id);

    List<Payment> getPaymentByCustomerId(Long customerId);

    List<Payment> getPaymentByRentedId(Long rentedId);
}
