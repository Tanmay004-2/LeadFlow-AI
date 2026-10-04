package com.leadflow.campaign.repository;

     import com.leadflow.campaign.dto.PublicCampaignProjection;
     import com.leadflow.campaign.entity.Campaign;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.data.jpa.repository.Query;
     import org.springframework.data.repository.query.Param;

     import java.util.Optional;
     import java.util.UUID;

     public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

           @Query(value = "SELECT c.id as id, c.name as name, c.business_id as businessId, b.name as businessName " +
                          "FROM campaigns c " +
                          "JOIN businesses b ON c.business_id = b.id " +
                          "WHERE c.id = :id", nativeQuery = true)
           Optional<PublicCampaignProjection> findPublicById(@Param("id") UUID id);
     }
