package com.leadflow.chat.repository;

     import com.leadflow.chat.entity.Lead;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.data.jpa.repository.Query;
     import org.springframework.stereotype.Repository;

     import java.util.List;
     import java.util.Optional;
     import java.util.UUID;

     @Repository
     public interface LeadRepository extends JpaRepository<Lead, UUID> {
         Optional<Lead> findBySessionId(UUID sessionId);

           long countByStatusIn(List<String> statuses);

           @Query(value = "SELECT COUNT(*) FROM leads", nativeQuery = true)
           long countAllGlobally();
     }
