package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarRental;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.model.Payment;
import com.lydiaplullc.CarLeasing.repository.PaymentRepository;
import com.lydiaplullc.CarLeasing.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final CustomerService customerService;
    private final CarRentalService carRentalService;

    @Override
    public Payment addPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        setPaymentModel(payment, paymentRequest);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment payment = getPaymentById(id);
        setPaymentModel(payment, paymentRequest);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found with ID: " + id));
    }

    @Override
    public List<Payment> getPaymentAll(){
        return paymentRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deletePayment(Long id) {
        getPaymentById(id);
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getPaymentByCustomerId(Long customerId) {
        return paymentRepository.findPaymentByCustomerId(customerId);
    }

    @Override
    public List<Payment> getPaymentByRentalId(Long rentalId) {
        return paymentRepository.findPaymentByRentalId(rentalId);
    }

    private void setPaymentModel(Payment payment, PaymentRequest paymentRequest) {
        Customer customer = customerService.getCustomerById(paymentRequest.getCustomerId());
        CarRental carRental = carRentalService.getCarRentalById(paymentRequest.getRentalId());

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setCustomer(customer);
        payment.setCarRental(carRental);
    }
}
