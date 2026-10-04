package com.leadflow.account.controller;

     import com.leadflow.account.dto.AccountDto;
     import com.leadflow.account.service.AccountService;
     import com.leadflow.core.api.ApiResponse;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.access.prepost.PreAuthorize;
     import org.springframework.web.bind.annotation.GetMapping;
     import org.springframework.web.bind.annotation.RequestMapping;
     import org.springframework.web.bind.annotation.RestController;

     import java.util.List;

     @RestController
     @RequestMapping("/api/v1/accounts")
     @RequiredArgsConstructor
     public class AccountController {

           private final AccountService accountService;

           @GetMapping
           @PreAuthorize("hasRole('PLATFORM_ADMIN')")
           public ResponseEntity<ApiResponse<List<AccountDto>>> getAccounts() {
               return ResponseEntity.ok(ApiResponse.success(accountService.getAllAccounts(), "Accounts retrieved successfully"));
           }
     }
