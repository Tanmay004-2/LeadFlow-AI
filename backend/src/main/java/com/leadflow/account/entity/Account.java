package com.leadflow.account.entity;

     import com.leadflow.core.entity.BaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     @Entity
     @Table(name = "accounts")
     @Getter
     @Setter
     public class Account extends BaseEntity {

           @Column(nullable = false)
           private String name;
     }
