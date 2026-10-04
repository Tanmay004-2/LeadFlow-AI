package com.leadflow.knowledge.repository;

     import com.leadflow.knowledge.entity.BusinessKnowledge;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.data.repository.NoRepositoryBean;

     import java.util.UUID;







     /**
       * Base repository interface.
       * The concrete implementation used by ChatService is BusinessKnowledgeRepository.
       */
     @NoRepositoryBean
     public interface KnowledgeRepository extends JpaRepository<BusinessKnowledge, UUID> {
     }
