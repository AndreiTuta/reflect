package com.at.reflect.service;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.PaymentDao;
import com.at.reflect.model.request.PaymentRequest;
import com.at.reflect.model.response.PaymentResponse;
import com.reflect.generated.tables.pojos.Payment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentService implements Service {

    private final PaymentDao paymentDao;
    private final ModelMapper modelMapper;

    private PaymentResponse.PaymentResponseBuilder buildPaymentResponse(final Payment payment) {
        return PaymentResponse.builder();
    }

    @Override
    public ServiceType getType() {
        return ServiceType.USER;
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
        final Payment payment = modelMapper.map(paymentRequest, Payment.class);
        paymentDao.insert(payment);
        return buildPaymentResponse(payment).build();
    }

}
