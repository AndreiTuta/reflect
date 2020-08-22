package com.at.reflect.service;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.at.reflect.model.request.PaymentRequest;
import com.at.reflect.model.response.PaymentResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentService implements Service {

    @Override
    public ServiceType getType() {
        return ServiceType.PAYMENT;
    }

    public void updatePayment(String paymentId, @Valid PaymentRequest paymentRequest) {
        // TODO Auto-generated method stub

    }

    public PaymentResponse delete(String paymentId) {
        // TODO Auto-generated method stub
        return null;
    }

    public PaymentResponse fetchPaymentById(String paymentId) {
        // TODO Auto-generated method stub
        return null;
    }

    public PaymentResponse createPayment(@Valid PaymentRequest paymentRequest) {
        // TODO Auto-generated method stub
        return null;
    }

}
