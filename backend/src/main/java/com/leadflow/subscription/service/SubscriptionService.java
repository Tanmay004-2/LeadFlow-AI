package com.leadflow.subscription.service;

import com.leadflow.subscription.entity.Subscription;
import com.leadflow.subscription.entity.SubscriptionStatus;
import com.leadflow.subscription.payment.PaymentGatewayProvider;
import com.leadflow.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentGatewayProvider paymentGateway;

    @Transactional
    public Subscription provisionTrial(UUID accountId) {
        Subscription subscription = new Subscription();
        subscription.setAccountId(accountId);
        subscription.setStatus(SubscriptionStatus.TRIAL);
        subscription.setTrialEndsAt(LocalDateTime.now().plusDays(7));
        return subscriptionRepository.save(subscription);
    }

    @Transactional(readOnly = true)
    public boolean hasActiveAccess(UUID accountId) {
        Subscription sub = subscriptionRepository.findByAccountId(accountId).orElse(null);
        if (sub == null) return false;
        return switch (sub.getStatus()) {
            case ACTIVE -> true;
            case TRIAL -> sub.getTrialEndsAt() != null && LocalDateTime.now().isBefore(sub.getTrialEndsAt());
            case PAST_DUE, CANCELLED -> false;
        };
    }
}
