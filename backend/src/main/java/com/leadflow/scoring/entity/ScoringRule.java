package com.leadflow.scoring.entity;

import com.leadflow.core.entity.TenantBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scoring_rules")
@Getter
@Setter
public class ScoringRule extends TenantBaseEntity {
    @Column(name = "rule_type", nullable = false)
    private String ruleType;
    @Column(name = "condition_value", nullable = false)
    private String conditionValue;
    @Column(name = "score_delta", nullable = false)
    private int scoreDelta;
}
