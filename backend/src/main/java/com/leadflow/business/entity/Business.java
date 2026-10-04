package com.leadflow.business.entity;

     import com.leadflow.core.entity.BaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     import java.util.UUID;

     @Entity
     @Table(name = "businesses")
     @Getter
     @Setter
     public class Business extends BaseEntity {

           @Column(name = "account_id", nullable = false)
           private UUID accountId;

           @Column(nullable = false)
           private String name;
     }
