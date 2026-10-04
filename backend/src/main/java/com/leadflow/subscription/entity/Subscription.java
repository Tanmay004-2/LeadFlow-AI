package com.leadflow.subscription.entity;

import com.leadflow.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription extends BaseEntity {
    @Column(name = "account_id", nullable = false, unique = true)
    private UUID accountId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;
    @Column(name = "trial_ends_at")
    private LocalDateTime trialEndsAt;
    @Column(name = "current_period_end")
    private LocalDateTime currentPeriodEnd;
}
