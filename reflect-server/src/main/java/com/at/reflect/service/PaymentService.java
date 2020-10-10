package com.at.reflect.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.PaymentDao;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
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
        return PaymentResponse.builder()
                              .id(payment.getId())
                              .amount(payment.getAmount())
                              .transactionId(payment.getTransactionId())
                              .created(payment.getCreated())
                              .amount(payment.getAmount());
    }

    @Override
    public ServiceType getType() {
        return ServiceType.PAYMENT;
    }

    public PaymentResponse updatePayment(String paymentId, @Valid PaymentRequest paymentRequest) throws NotFoundException {
        try {
            int id = Integer.parseInt(paymentId);
            fetchOptionalPaymentById(id)
                                        .orElseThrow(() -> new NotFoundException("Payment with id: " + paymentId + " not found"));

            final Payment payment = modelMapper.map(paymentRequest, Payment.class);
            payment.setId(id);
            paymentDao.update(payment);
            return buildPaymentResponse(payment).build();
        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    public PaymentResponse delete(String paymentId) throws NotFoundException {
        try {
            int id = Integer.parseInt(paymentId);
            Payment formerPayment = fetchOptionalPaymentById(id)
                                                                .orElseThrow(() -> new NotFoundException("Payment with id: " + paymentId
                                                                    + " not found"));
            paymentDao.delete(formerPayment);
            return buildPaymentResponse(formerPayment).build();

        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    public PaymentResponse fetchPaymentById(String paymentId) throws NotFoundException {
        try {
            int id = Integer.parseInt(paymentId);
            final Payment payment = fetchOptionalPaymentById(id)
                                                                .orElseThrow(() -> new NotFoundException("Payment with id: "
                                                                    + paymentId + " not found"));
            return buildPaymentResponse(payment).build();
        } catch (NumberFormatException e) {
            throw new PathException("paymentId on path must be an integer");
        }
    }

    public Optional<Payment> fetchOptionalPaymentById(final Integer paymentId) {
        return Optional.ofNullable(paymentDao.findById(paymentId));
    }

    public PaymentResponse createPayment(@Valid PaymentRequest paymentRequest) {
        final Payment payment = modelMapper.map(paymentRequest, Payment.class);
        paymentDao.insert(payment);
        return buildPaymentResponse(payment).build();
    }
}
