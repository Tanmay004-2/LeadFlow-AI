package com.leadflow.chat.entity;

     import com.leadflow.core.entity.TenantBaseEntity;





     import jakarta.persistence.Column;
     import jakarta.persistence.Entity;
     import jakarta.persistence.EnumType;
     import jakarta.persistence.Enumerated;
     import jakarta.persistence.Table;
     import lombok.Getter;
     import lombok.Setter;

     import java.util.UUID;

     @Entity
     @Table(name = "messages")
     @Getter
     @Setter
     public class Message extends TenantBaseEntity {

           @Column(name = "conversation_id", nullable = false)
           private UUID conversationId;

           @Enumerated(EnumType.STRING)
           @Column(name = "sender_type", nullable = false)
           private SenderType senderType;

           @Column(name = "content", nullable = false, columnDefinition = "TEXT")
           private String content;
     }
