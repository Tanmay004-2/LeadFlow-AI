package com.leadflow.subscription.payment;

public interface PaymentGatewayProvider {
    String createCustomer(String email, String name);
    String createSubscription(String customerId, String planId);
    boolean cancelSubscription(String externalSubscriptionId);
    boolean verifyPaymentStatus(String externalSubscriptionId);
}
