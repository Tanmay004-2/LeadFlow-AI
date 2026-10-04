package com.leadflow.subscription.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
public class MockPaymentProvider implements PaymentGatewayProvider {
    public String createCustomer(String email, String name) {
        log.info("Mocking customer creation for {}", email);
        return "cus_mock_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public String createSubscription(String customerId, String planId) {
        log.info("Mocking subscription creation for customer {} on plan {}", customerId, planId);
        return "sub_mock_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public boolean cancelSubscription(String externalSubscriptionId) {
        log.info("Mocking subscription cancellation for {}", externalSubscriptionId);
        return true;
    }
    public boolean verifyPaymentStatus(String externalSubscriptionId) { return true; }
}
