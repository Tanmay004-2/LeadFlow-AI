package com.leadflow.knowledge.controller;

     import com.leadflow.business.service.BusinessService;
     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.core.tenant.TenantContext;
     import com.leadflow.knowledge.dto.KnowledgeDto;
     import com.leadflow.knowledge.service.KnowledgeService;
     import com.leadflow.security.AuthUser;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.core.annotation.AuthenticationPrincipal;
     import org.springframework.web.bind.annotation.*;

     import java.util.List;
     import java.util.UUID;

     @RestController
     @RequestMapping("/api/v1/businesses/{businessId}/knowledge")
     @RequiredArgsConstructor
     public class KnowledgeController {

           private final KnowledgeService knowledgeService;
           private final BusinessService businessService;






           @GetMapping
           public ResponseEntity<ApiResponse<List<KnowledgeDto>>> getKnowledge(
                   @PathVariable UUID businessId,
                   @AuthenticationPrincipal AuthUser authUser) {

                businessService.validateBusinessAccess(authUser, businessId);
                TenantContext.setTenantId(businessId);
                try {
                    return ResponseEntity.ok(ApiResponse.success(knowledgeService.getAllKnowledge(), "Knowledge retrieved"));
                } finally {
                    TenantContext.clear();
                }
           }

           @PostMapping
           public ResponseEntity<ApiResponse<KnowledgeDto>> addKnowledge(
                   @PathVariable UUID businessId,
                   @RequestBody KnowledgeDto request,
                   @AuthenticationPrincipal AuthUser authUser) {

                businessService.validateBusinessAccess(authUser, businessId);
                TenantContext.setTenantId(businessId);
                try {
                    return ResponseEntity.ok(ApiResponse.success(knowledgeService.addKnowledge(request), "Knowledge added"));
                } finally {
                    TenantContext.clear();
                }
           }

           @DeleteMapping("/{knowledgeId}")
           public ResponseEntity<ApiResponse<Void>> deleteKnowledge(
                   @PathVariable UUID businessId,
                   @PathVariable UUID knowledgeId,
                   @AuthenticationPrincipal AuthUser authUser) {

                businessService.validateBusinessAccess(authUser, businessId);
                TenantContext.setTenantId(businessId);
                try {
                    knowledgeService.deleteKnowledge(knowledgeId);
                    return ResponseEntity.ok(ApiResponse.success(null, "Knowledge deleted"));
                } finally {
                    TenantContext.clear();
                }
           }
     }
