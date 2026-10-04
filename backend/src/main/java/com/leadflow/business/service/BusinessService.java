package com.leadflow.business.service;

     import com.leadflow.business.dto.BusinessDto;
     import com.leadflow.business.entity.Business;
     import com.leadflow.business.repository.BusinessRepository;
     import com.leadflow.security.AuthUser;
     import lombok.RequiredArgsConstructor;
     import org.springframework.stereotype.Service;
     import org.springframework.transaction.annotation.Transactional;

     import java.util.List;
     import java.util.UUID;
     import java.util.stream.Collectors;

     @Service
     @RequiredArgsConstructor
     public class BusinessService {

           private final BusinessRepository businessRepository;

           public void validateBusinessAccess(AuthUser authUser, UUID businessId) {
               if ("PLATFORM_ADMIN".equals(authUser.getRole().name())) {
                   return;
               }

                if ("ACCOUNT_OWNER".equals(authUser.getRole().name())) {
                    Business business = businessRepository.findById(businessId)
                            .orElseThrow(() -> new IllegalArgumentException("Business not found"));
                    if (!business.getAccountId().equals(authUser.getAccountId())) {
                        throw new SecurityException("Access Denied: Business belongs to another account");
                    }
                    return;
                }

                if (!businessId.equals(authUser.getBusinessId())) {
                    throw new SecurityException("Access Denied: You do not have access to this business");
                }
           }

           @Transactional(readOnly = true)
           public List<BusinessDto> getBusinessesForAccount(UUID accountId) {
               return businessRepository.findByAccountId(accountId).stream().map(this::mapToDto).collect(Collectors.toList());
           }

           private BusinessDto mapToDto(Business business) {
               BusinessDto dto = new BusinessDto();
               dto.setId(business.getId());
               dto.setAccountId(business.getAccountId());
               dto.setName(business.getName());
               return dto;







           }
     }
