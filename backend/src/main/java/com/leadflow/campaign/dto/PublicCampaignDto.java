package com.leadflow.campaign.dto;

     import lombok.Data;

     import java.util.UUID;

     @Data
     public class PublicCampaignDto {
         private UUID id;
         private String name;
         private UUID businessId;
         private String businessName;
     }
