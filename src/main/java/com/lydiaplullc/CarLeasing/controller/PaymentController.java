package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.model.Payment;
import com.lydiaplullc.CarLeasing.request.PaymentRequest;
import com.lydiaplullc.CarLeasing.response.CarRentalResponse;
import com.lydiaplullc.CarLeasing.response.CustomerResponse;
import com.lydiaplullc.CarLeasing.response.PaymentResponse;
import com.lydiaplullc.CarLeasing.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/add/new-payment")
    public ResponseEntity<PaymentResponse> addPayment(
            @RequestBody PaymentRequest paymentRequest
    ) {
        Payment savedPayment = paymentService.addPayment(paymentRequest);
        PaymentResponse paymentResponse = getPaymentResponse(savedPayment);
        return ResponseEntity.ok(paymentResponse);
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable Long paymentId,
            @RequestBody PaymentRequest paymentRequest
    ){
        Payment savedPayment = paymentService.updatePayment(paymentId, paymentRequest);
        PaymentResponse paymentResponse = getPaymentResponse(savedPayment);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long paymentId
    ) {
        Payment payment = paymentService.getPaymentById(paymentId);
        PaymentResponse paymentResponse = getPaymentResponse(payment);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponse>> getAllPayments(){
        List<Payment> payments = paymentService.getPaymentAll();

        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: payments) {
            PaymentResponse paymentResponse = getPaymentResponse(payment);
            paymentResponses.add(paymentResponse);
        }

        return ResponseEntity.ok(paymentResponses);
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long paymentId
    ) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerId(
            @PathVariable Long customerId
    ) {
        List<Payment> payments = paymentService.getPaymentByCustomerId(customerId);

        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: payments) {
            PaymentResponse paymentResponse = getPaymentResponse(payment);
            paymentResponses.add(paymentResponse);
        }

        return ResponseEntity.ok(paymentResponses);
    }

    @GetMapping("/by-rental-id/{rentalId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByRentalId(
            @PathVariable Long rentalId
    ) {
        List<Payment> payments = paymentService.getPaymentByRentalId(rentalId);

        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: payments) {
            PaymentResponse paymentResponse = getPaymentResponse(payment);
            paymentResponses.add(paymentResponse);
        }

        return ResponseEntity.ok(paymentResponses);
    }

    public static PaymentResponse getPaymentResponse(Payment payment) {
        CustomerResponse customerResponse = CustomerController.getCustomerResponse(payment.getCustomer());
        CarRentalResponse carRentalResponse = CarRentalController.getCarRentalResponse(payment.getCarRental());

        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                customerResponse,
                carRentalResponse
        );
    }
}
