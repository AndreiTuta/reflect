package com.at.reflect.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.PaymentRequest;
import com.at.reflect.model.response.PaymentResponse;
import com.at.reflect.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/payment")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Rest API to interact with Payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Add a Payment")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> addPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.debug("Creating new payment...");
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(paymentRequest));
    }

    @Operation(summary = "Fetch Payment by ID")
    @GetMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> fetchPayment(@PathVariable String paymentId) throws NotFoundException {
        log.debug("Fetching payment...");
        return ResponseEntity.ok(paymentService.fetchPaymentById(paymentId));
    }

    @Operation(summary = "Update Payment by ID")
    @PutMapping(value = "/{paymentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void udpatePayment(@PathVariable String paymentId, @RequestBody @Valid PaymentRequest paymentRequest)
                                                                                                         throws NotFoundException {
        log.debug("Updating payment...");
        paymentService.updatePayment(paymentId, paymentRequest);
    }

    @Operation(summary = "Delete Payment by ID")
    @DeleteMapping(value = "/{paymentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<PaymentResponse> del(@PathVariable String paymentId, @RequestBody @Valid PaymentRequest paymentRequest)
                                                                                                 throws NotFoundException {
        log.debug("Updating payment...");
        paymentService.updatePayment(paymentId, paymentRequest);
        return ResponseEntity.ok(paymentService.delete(paymentId));
    }

}
