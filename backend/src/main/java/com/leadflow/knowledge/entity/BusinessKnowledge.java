package com.leadflow.knowledge.entity;

     import com.leadflow.core.entity.TenantBaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.EnumType;
     import jakarta.persistence.Enumerated;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     @Entity
     @Table(name = "business_knowledge")
     @Getter
     @Setter
     public class BusinessKnowledge extends TenantBaseEntity {

           @Enumerated(EnumType.STRING)
           @Column(name = "type", nullable = false)
           private KnowledgeType type;

           @Column(name = "content", nullable = false, columnDefinition = "TEXT")
           private String content;

           @Column(name = "is_active", nullable = false)
           private Boolean isActive = true;
     }
