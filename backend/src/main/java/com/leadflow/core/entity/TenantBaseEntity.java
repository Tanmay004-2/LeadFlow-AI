package com.leadflow.core.entity;

     import jakarta.persistence.Column;
     import jakarta.persistence.MappedSuperclass;





     import lombok.Getter;
     import lombok.Setter;
     import org.hibernate.annotations.TenantId;

     import java.util.UUID;

     @MappedSuperclass
     @Getter
     @Setter
     public abstract class TenantBaseEntity extends BaseEntity {

           @TenantId
           @Column(name = "business_id", nullable = false, updatable = false)
           private UUID businessId;
     }
