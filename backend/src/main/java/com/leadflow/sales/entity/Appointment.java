package com.leadflow.sales.entity;

import com.leadflow.core.entity.TenantBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment extends TenantBaseEntity {
    @Column(name = "lead_id", nullable = false)
    private UUID leadId;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    private String status = "SCHEDULED";
}
