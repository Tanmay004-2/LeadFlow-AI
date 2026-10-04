package com.leadflow.campaign.controller;

     import com.leadflow.campaign.dto.PublicCampaignDto;
     import com.leadflow.campaign.service.CampaignService;
     import com.leadflow.core.api.ApiResponse;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.web.bind.annotation.GetMapping;
     import org.springframework.web.bind.annotation.PathVariable;
     import org.springframework.web.bind.annotation.RequestMapping;
     import org.springframework.web.bind.annotation.RestController;

     import java.util.UUID;

     @RestController
     @RequestMapping("/api/v1/public/campaigns")
     @RequiredArgsConstructor
     public class PublicCampaignController {

           private final CampaignService campaignService;

           @GetMapping("/{id}")
           public ResponseEntity<ApiResponse<PublicCampaignDto>> getPublicCampaign(@PathVariable UUID id) {
               return ResponseEntity.ok(ApiResponse.success(campaignService.getPublicCampaign(id), "Public campaign retrieved"));
           }
     }
