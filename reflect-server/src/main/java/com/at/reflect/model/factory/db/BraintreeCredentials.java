package com.at.reflect.model.factory.db;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BraintreeCredentials
{
        private String merchantId;
        private String publicKey;
        private String privateKey;
        private String planId;

}