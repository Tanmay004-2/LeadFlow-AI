package com.leadflow.user.entity;

     import com.leadflow.core.entity.BaseEntity;
     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.EnumType;
     import jakarta.persistence.Enumerated;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     import java.util.UUID;

     @Entity
     @Table(name = "users")
     @Getter
     @Setter
     public class User extends BaseEntity {

           @Column(name = "account_id")
           private UUID accountId;

           @Column(name = "business_id")
           private UUID businessId;

           @Column(nullable = false, unique = true)
           private String email;

           @Column(nullable = false)
           private String password;

           @Enumerated(EnumType.STRING)
           @Column(nullable = false)
           private Role role;
     }
