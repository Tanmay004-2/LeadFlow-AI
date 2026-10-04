package com.leadflow.subscription.repository;

import com.leadflow.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByAccountId(UUID accountId);
}
