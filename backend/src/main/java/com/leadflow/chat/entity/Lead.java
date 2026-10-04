package com.leadflow.chat.entity;

     import com.leadflow.core.entity.TenantBaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;
     import org.hibernate.annotations.JdbcTypeCode;
     import org.hibernate.type.SqlTypes;

     import java.util.Map;
     import java.util.UUID;

     @Entity
     @Table(name = "leads")
     @Getter





     @Setter
     public class Lead extends TenantBaseEntity {

           @Column(name = "campaign_id", nullable = false)
           private UUID campaignId;

           @Column(name = "session_id", nullable = false, unique = true)
           private UUID sessionId;

           @Column(name = "score")
           private Integer score = 0;

           @Column(name = "status", nullable = false)
           private String status = "COLD";

           @JdbcTypeCode(SqlTypes.JSON)
           @Column(name = "extracted_data", columnDefinition = "jsonb")
           private Map<String, Object> extractedData;
     }
