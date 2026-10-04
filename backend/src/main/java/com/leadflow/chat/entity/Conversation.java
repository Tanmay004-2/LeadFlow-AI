package com.leadflow.chat.entity;

     import com.leadflow.core.entity.TenantBaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     import java.util.UUID;

     @Entity
     @Table(name = "conversations")
     @Getter
     @Setter
     public class Conversation extends TenantBaseEntity {

           @Column(name = "lead_id", nullable = false)
           private UUID leadId;

           @Column(name = "agent_id")
           private UUID agentId;

           @Column(name = "status", nullable = false)
           private String status = "ACTIVE";
     }
