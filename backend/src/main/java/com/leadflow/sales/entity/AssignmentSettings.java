package com.leadflow.sales.entity;

import com.leadflow.core.entity.TenantBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "assignment_settings")
@Getter
@Setter
public class AssignmentSettings extends TenantBaseEntity {
    @Column(nullable = false)
    private String strategy = "ROUND_ROBIN";
    @Column(name = "last_assigned_agent_id")
    private UUID lastAssignedAgentId;
}
