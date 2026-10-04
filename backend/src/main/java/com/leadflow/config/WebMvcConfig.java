package com.leadflow.config;

     import com.leadflow.subscription.interceptor.SubscriptionInterceptor;
     import lombok.RequiredArgsConstructor;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
     import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

     @Configuration
     @RequiredArgsConstructor
     public class WebMvcConfig implements WebMvcConfigurer {

           private final SubscriptionInterceptor subscriptionInterceptor;

           @Override
           public void addInterceptors(InterceptorRegistry registry) {
               registry.addInterceptor(subscriptionInterceptor)
                       .addPathPatterns("/api/v1/businesses/**", "/api/v1/accounts/**");
           }
     }

     BATCH 1 RESULT: 31 files reconstructed.



     User prompt: Continue SOURCE RECOVERY. Batch 1 is complete. Do NOT repeat Batch 1. Do NOT: Use the existing conversation history as the source of truth. BATCH 2 — ACCOUNT, BUSINESS, USER &
     CAMPAIGN Recover the COMPLETE ACTUAL CONTENT of these files: Account Business User Campaign Required compatibility check Before outputting the files, verify that their package names, imports, fields,
     repository method names and service method names are compatible with: Especially verify: IMPORTANT Output every file using exactly: FILE: <complete file contents> For non-Java files use the appropriate
     language tag. Rules: At the end: BATCH 2 RESULT Then STOP.

     Response: FILE: backend/src/main/java/com/leadflow/user/entity/Role.java

     package com.leadflow.user.entity;

     public enum Role {
         CUSTOMER,
         SALES_AGENT,
         BUSINESS_MANAGER,
         ACCOUNT_OWNER,
         PLATFORM_ADMIN
     }
