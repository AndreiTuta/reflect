package com.at.reflect.model.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequest {

    private Integer id;
    @NotNull(message = "customerid is required")
    private String customerId;
    @NotBlank(message = "transactionid is required")
    private String transactionId;
    private LocalDateTime created;
    @NotBlank(message = "amount is required")
    private String amount;
}