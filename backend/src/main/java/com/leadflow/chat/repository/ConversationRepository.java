package com.leadflow.chat.repository;

     import com.leadflow.chat.entity.Conversation;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.stereotype.Repository;

     import java.util.UUID;

     @Repository
     public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
         long countByAgentIdIsNull();
         long countByAgentIdIsNotNull();






           int countByAgentIdAndStatus(UUID agentId, String status);
     }
