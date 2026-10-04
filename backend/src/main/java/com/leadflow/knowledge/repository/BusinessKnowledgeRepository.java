package com.leadflow.knowledge.repository;

     import com.leadflow.knowledge.entity.BusinessKnowledge;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.stereotype.Repository;

     import java.util.List;
     import java.util.UUID;

     @Repository
     public interface BusinessKnowledgeRepository extends JpaRepository<BusinessKnowledge, UUID> {

           List<BusinessKnowledge> findByIsActiveTrue();
     }
