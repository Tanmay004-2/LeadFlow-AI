package com.leadflow.campaign.dto;

     import lombok.Data;

     import java.util.UUID;

     @Data
     public class CampaignDto {
         private UUID id;
         private UUID businessId;
         private String name;
     }
