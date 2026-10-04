package com.leadflow.business.dto;

     import lombok.Data;

     import java.util.UUID;

     @Data
     public class BusinessDto {
         private UUID id;
         private UUID accountId;
         private String name;
     }
