package com.leadflow.campaign.controller;

     import com.leadflow.business.service.BusinessService;
     import com.leadflow.campaign.dto.CampaignDto;
     import com.leadflow.campaign.service.CampaignService;
     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.core.tenant.TenantContext;
     import com.leadflow.security.AuthUser;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.core.annotation.AuthenticationPrincipal;
     import org.springframework.web.bind.annotation.*;

     import java.util.List;
     import java.util.UUID;

     @RestController
     @RequestMapping("/api/v1/businesses/{businessId}/campaigns")
     @RequiredArgsConstructor
     public class CampaignController {

           private final CampaignService campaignService;
           private final BusinessService businessService;

           @GetMapping
           public ResponseEntity<ApiResponse<List<CampaignDto>>> getCampaigns(





                     @PathVariable UUID businessId,
                     @AuthenticationPrincipal AuthUser authUser) {

                businessService.validateBusinessAccess(authUser, businessId);
                TenantContext.setTenantId(businessId);
                try {
                    return ResponseEntity.ok(ApiResponse.success(campaignService.getAllCampaigns(), "Campaigns retrieved successfully"));
                } finally {
                    TenantContext.clear();
                }
           }

           @PostMapping
           public ResponseEntity<ApiResponse<CampaignDto>> createCampaign(
                   @PathVariable UUID businessId,
                   @RequestBody CampaignDto request,
                   @AuthenticationPrincipal AuthUser authUser) {

                businessService.validateBusinessAccess(authUser, businessId);
                TenantContext.setTenantId(businessId);
                try {
                    return ResponseEntity.ok(ApiResponse.success(campaignService.createCampaign(request), "Campaign created successfully"));
                } finally {
                    TenantContext.clear();
                }
           }
     }
