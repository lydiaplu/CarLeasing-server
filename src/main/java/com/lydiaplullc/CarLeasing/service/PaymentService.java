package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Customer;
import com.lydiaplullc.CarLeasing.model.Payment;
import com.lydiaplullc.CarLeasing.model.RentedCar;
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
    private final RentedCarService rentedCarService;

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
    public List<Payment> getPaymentByRentedId(Long rentedId) {
        return paymentRepository.findPaymentByRentedId(rentedId);
    }

    private void setPaymentModel(Payment payment, PaymentRequest paymentRequest) {
        Customer customer = customerService.getCustomerById(paymentRequest.getCustomerId());
        RentedCar rentedCar = rentedCarService.getRentedCarById(paymentRequest.getRentedId());

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setCustomer(customer);
        payment.setRentedCar(rentedCar);
    }
}
