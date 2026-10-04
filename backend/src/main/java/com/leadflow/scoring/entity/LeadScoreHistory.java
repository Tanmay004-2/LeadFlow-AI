package com.leadflow.scoring.entity;

import com.leadflow.core.entity.TenantBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "lead_score_history")
@Getter
@Setter
public class LeadScoreHistory extends TenantBaseEntity {
    @Column(name = "lead_id", nullable = false)
    private UUID leadId;
    @Column(name = "old_score", nullable = false)
    private int oldScore;
    @Column(name = "new_score", nullable = false)
    private int newScore;
    @Column(name = "reason")
    private String reason;
}
