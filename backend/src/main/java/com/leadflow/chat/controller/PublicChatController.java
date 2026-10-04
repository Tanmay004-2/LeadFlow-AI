package com.leadflow.chat.controller;

import com.leadflow.campaign.entity.Campaign;
import com.leadflow.campaign.repository.CampaignRepository;
import com.leadflow.chat.entity.Conversation;
import com.leadflow.chat.entity.Lead;
import com.leadflow.chat.repository.ConversationRepository;
import com.leadflow.chat.repository.LeadRepository;
import com.leadflow.core.api.ApiResponse;
import com.leadflow.security.JwtService;
import com.leadflow.user.entity.Role;
import com.leadflow.user.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/campaigns")
@RequiredArgsConstructor
public class PublicChatController {

    private final CampaignRepository campaignRepository;
    private final LeadRepository leadRepository;
    private final ConversationRepository conversationRepository;
    private final JwtService jwtService;

    @PostMapping("/{campaignId}/chat/init")
    @Transactional
    public ResponseEntity<ApiResponse<ChatInitResponse>> initChat(@PathVariable UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        UUID sessionId = UUID.randomUUID();

        Lead lead = new Lead();
        lead.setBusinessId(campaign.getBusinessId());
        lead.setCampaignId(campaignId);
        lead.setSessionId(sessionId);
        lead.setStatus("COLD");
        lead = leadRepository.save(lead);

        Conversation conversation = new Conversation();
        conversation.setBusinessId(campaign.getBusinessId());
        conversation.setLeadId(lead.getId());
        conversation.setStatus("ACTIVE");
        conversation = conversationRepository.save(conversation);

        User customerUser = new User();
        customerUser.setId(sessionId);
        customerUser.setBusinessId(campaign.getBusinessId());
        customerUser.setRole(Role.CUSTOMER);
        customerUser.setEmail("anonymous_" + sessionId.toString().substring(0, 8) + "@customer.local");

        String token = jwtService.generateToken(customerUser);

        ChatInitResponse response = new ChatInitResponse(
                conversation.getId(), sessionId, token,
                campaign.getBusinessId(), campaignId);

        return ResponseEntity.ok(ApiResponse.success(response, "Chat initialized successfully"));
    }

    @Data
    public static class ChatInitResponse {
        private final UUID conversationId;
        private final UUID sessionId;
        private final String token;
        private final UUID businessId;
        private final UUID campaignId;
    }
}
