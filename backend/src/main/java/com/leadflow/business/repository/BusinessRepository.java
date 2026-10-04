package com.leadflow.business.repository;

     import com.leadflow.business.entity.Business;
     import org.springframework.data.jpa.repository.JpaRepository;

     import java.util.List;
     import java.util.UUID;

     public interface BusinessRepository extends JpaRepository<Business, UUID> {
         List<Business> findByAccountId(UUID accountId);
     }
