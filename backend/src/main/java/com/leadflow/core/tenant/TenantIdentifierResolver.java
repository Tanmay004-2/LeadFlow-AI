package com.leadflow.core.tenant;

     import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
     import org.springframework.stereotype.Component;

     import java.util.UUID;

     @Component
     public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<UUID> {

           @Override
           public UUID resolveCurrentTenantIdentifier() {
               UUID tenantId = TenantContext.getTenantId();
               if (tenantId != null) {
                   return tenantId;
               }
               return UUID.fromString("00000000-0000-0000-0000-000000000000"); // Fallback for global context
           }

           @Override





           public boolean validateExistingCurrentSessions() {
               return true;
           }
     }
