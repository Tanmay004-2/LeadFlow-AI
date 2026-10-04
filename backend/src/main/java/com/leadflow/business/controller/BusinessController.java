package com.leadflow.business.controller;

     import com.leadflow.business.dto.BusinessDto;
     import com.leadflow.business.service.BusinessService;
     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.security.AuthUser;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.core.annotation.AuthenticationPrincipal;
     import org.springframework.web.bind.annotation.GetMapping;
     import org.springframework.web.bind.annotation.PathVariable;
     import org.springframework.web.bind.annotation.RequestMapping;
     import org.springframework.web.bind.annotation.RestController;

     import java.util.List;
     import java.util.UUID;

     @RestController
     @RequestMapping("/api/v1/accounts/{accountId}/businesses")
     @RequiredArgsConstructor
     public class BusinessController {

           private final BusinessService businessService;

           @GetMapping
           public ResponseEntity<ApiResponse<List<BusinessDto>>> getBusinesses(
                   @PathVariable UUID accountId,
                   @AuthenticationPrincipal AuthUser authUser) {

                if (!"PLATFORM_ADMIN".equals(authUser.getRole().name()) && !accountId.equals(authUser.getAccountId())) {
                    throw new SecurityException("Access Denied: You do not have access to this account");
                }

                return ResponseEntity.ok(ApiResponse.success(
                        businessService.getBusinessesForAccount(accountId),
                        "Businesses retrieved successfully"));
           }
     }
