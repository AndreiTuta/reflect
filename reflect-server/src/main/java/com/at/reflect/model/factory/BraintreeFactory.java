package com.at.reflect.model.factory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.at.reflect.model.factory.db.BraintreeCredentials;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.SubscriptionRequest;
import com.braintreegateway.TransactionRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class BraintreeFactory {

    public Result<Subscription> processSubscriptionRequest(final String customerId, final String currency,
                    final BraintreeCredentials credentials)
    {
            final BraintreeGateway gateway = createGateway(credentials);
            Result<Subscription> subscriptionRequest = null;
            Customer customer = gateway.customer()
                            .find(customerId);
            if (customer != null)
            {
                    log.debug("Customer found with id {}", customerId);
                    final String paymentMethodToken = customer.getPaymentMethods()
                                    .get(0)
                                    .getToken();
                    if (!StringUtils.isEmpty(paymentMethodToken))
                    {
                            final SubscriptionRequest request =
                                            createSubscriptionRequest(currency, paymentMethodToken, credentials.getPlanId());
                            subscriptionRequest = gateway.subscription()
                                            .create(request);
                    }
            }
            return subscriptionRequest;
    }
    
    public BraintreeGateway createGateway(final BraintreeCredentials credentials)
    {
            boolean production = false;
            return new BraintreeGateway(production ? Environment.PRODUCTION : Environment.SANDBOX, credentials.getMerchantId(),
                            credentials.getPublicKey(), credentials.getPrivateKey());
    }

    public CustomerRequest createCustomerRequest()
    {
            return new CustomerRequest();
    }

    public TransactionRequest createTransactionRequest()
    {
            return new TransactionRequest();
    }
    
    public SubscriptionRequest createSubscriptionRequest()
    {
            return new SubscriptionRequest();
    }

    private SubscriptionRequest createSubscriptionRequest(final String currency, final String paymentMethodToken,
                    final String planId)
    {
            log.info("Creating subscription request");
            SubscriptionRequest request = createSubscriptionRequest()
                            .paymentMethodToken(paymentMethodToken)
                            .planId(planId);

            return request;
    }
    
    

}

