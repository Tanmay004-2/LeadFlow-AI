package com.leadflow.nurture.entity;

import com.leadflow.core.entity.TenantBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "follow_up_tasks")
@Getter
@Setter
public class FollowUpTask extends TenantBaseEntity {
    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;
    @Column(name = "scheduled_for", nullable = false)
    private LocalDateTime scheduledFor;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowUpStatus status = FollowUpStatus.PENDING;
}
