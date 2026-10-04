package com.leadflow.campaign.entity;

     import com.leadflow.core.entity.TenantBaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     @Entity
     @Table(name = "campaigns")
     @Getter
     @Setter
     public class Campaign extends TenantBaseEntity {

           @Column(nullable = false)
           private String name;
     }
