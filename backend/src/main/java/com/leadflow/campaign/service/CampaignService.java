package com.leadflow.campaign.service;

     import com.leadflow.campaign.dto.CampaignDto;
     import com.leadflow.campaign.dto.PublicCampaignDto;
     import com.leadflow.campaign.dto.PublicCampaignProjection;
     import com.leadflow.campaign.entity.Campaign;
     import com.leadflow.campaign.repository.CampaignRepository;
     import lombok.RequiredArgsConstructor;
     import org.springframework.stereotype.Service;
     import org.springframework.transaction.annotation.Transactional;

     import java.util.List;
     import java.util.UUID;
     import java.util.stream.Collectors;

     @Service
     @RequiredArgsConstructor
     public class CampaignService {






           private final CampaignRepository campaignRepository;

           @Transactional(readOnly = true)
           public List<CampaignDto> getAllCampaigns() {
               return campaignRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
           }

           @Transactional(readOnly = true)
           public PublicCampaignDto getPublicCampaign(UUID id) {
               PublicCampaignProjection projection = campaignRepository.findPublicById(id)
                       .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

                PublicCampaignDto dto = new PublicCampaignDto();
                dto.setId(projection.getId());
                dto.setName(projection.getName());
                dto.setBusinessId(projection.getBusinessId());
                dto.setBusinessName(projection.getBusinessName());
                return dto;
           }

           @Transactional
           public CampaignDto createCampaign(CampaignDto dto) {
               Campaign campaign = new Campaign();
               campaign.setName(dto.getName());
               Campaign saved = campaignRepository.save(campaign);
               return mapToDto(saved);
           }

           private CampaignDto mapToDto(Campaign campaign) {
               CampaignDto dto = new CampaignDto();
               dto.setId(campaign.getId());
               dto.setBusinessId(campaign.getBusinessId());
               dto.setName(campaign.getName());
               return dto;
           }
     }
