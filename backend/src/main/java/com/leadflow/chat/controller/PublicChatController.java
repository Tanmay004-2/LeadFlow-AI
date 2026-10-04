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

                ChatInitResponse response = new ChatInitResponse(conversation.getId(), sessionId, token, campaign.getBusinessId(), campaignId);
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



     User prompt: Continue LeadFlow AI source recovery. Do NOT redesign the project. Do NOT create new phases. Do NOT regenerate files that have already been recovered. Do NOT modify existing recovered files
     yet. We are reconstructing the repository from the original conversation because no downloadable source artifact exists. ## BATCH 5 — PUBLIC CUSTOMER FRONTEND + REMAINING CHAT/CAMPAIGN
     DEPENDENCIES Recover ONLY these files: ### FRONTEND​1. frontend/src/app/c/[campaignId]/page.tsx 2. frontend/src/app/c/[campaignId]/ChatInterface.tsx ### BACKEND CHAT/CAMPAIGN DEPENDENCIES
     3. backend/src/main/java/com/leadflow/campaign/repository/CampaignRepository.java 4. backend/src/main/java/com/leadflow/campaign/entity/Campaign.java 5.
     backend/src/main/java/com/leadflow/campaign/dto/PublicCampaignDto.java 6. backend/src/main/java/com/leadflow/campaign/dto/PublicCampaignProjection.java 7.
     backend/src/main/java/com/leadflow/campaign/controller/PublicCampaignController.java 8. backend/src/main/java/com/leadflow/campaign/service/CampaignService.java ### CHAT SUPPORT 9.
     backend/src/main/java/com/leadflow/chat/repository/ConversationRepository.java 10. backend/src/main/java/com/leadflow/chat/repository/MessageRepository.java 11.
     backend/src/main/java/com/leadflow/chat/repository/LeadRepository.java IMPORTANT: Some of these files may already have been recovered in an earlier batch. If a file is ALREADY RECOVERED and the exact
     source was previously provided, DO NOT regenerate it. Instead state: FILE: <path> ALREADY RECOVERED — SKIPPED Only output files that are actually still missing or incomplete. ## FRONTEND
     COMPATIBILITY REQUIREMENTS The public customer flow must remain: Campaign URL → /c/[campaignId] → fetch public campaign → initialize anonymous chat → receive CUSTOMER JWT/session information
     → store session information in sessionStorage → create/send messages → receive AI responses → preserve conversationId → allow AI booking response to navigate to /book → /book uses the same
     leadflow_session → customer can return to /c/[campaignId] The frontend must remain compatible with the already recovered:​- CustomerChatController​- PublicChatController​- CustomerAppointmentController​-
     BookPage​- ChatService The existing sessionStorage key is: leadflow_session Do not change that key. The stored session must preserve the information required by BookPage, including:​- token​- businessId​-
     campaignId​- conversationId/sessionId as required by the existing flow Do not invent a different authentication mechanism. ## BACKEND COMPATIBILITY Campaign files must remain compatible with:​- Flyway V2​-
     TenantBaseEntity​- existing CampaignDto​- existing PublicCampaignDto​- existing PublicCampaignProjection​- existing CampaignController​- PublicChatController​- existing authentication/security model Do not modify
     Flyway migrations. Do not introduce new database columns. Do not introduce new dependencies. ## IMPORTANT SECURITY REQUIREMENTS Public campaign endpoints may be publicly accessible. Customer
     chat initialization must remain anonymous. Do NOT expose private business data through public campaign endpoints. Do NOT trust a customer-supplied businessId when the campaign itself can establish the
     business. Do NOT remove the existing JWT/session ownership model. ## RECOVERY RULE For every file: FILE: <exact path> ```language <complete source>

     Response: FILE: backend/src/main/java/com/leadflow/campaign/repository/CampaignRepository.java

     package com.leadflow.campaign.repository;

     import com.leadflow.campaign.dto.PublicCampaignProjection;
     import com.leadflow.campaign.entity.Campaign;
     import org.springframework.data.jpa.repository.JpaRepository;
     import org.springframework.data.jpa.repository.Query;
     import org.springframework.data.repository.query.Param;
     import org.springframework.stereotype.Repository;

     import java.util.Optional;
     import java.util.UUID;

     @Repository
     public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

           @Query(value = "SELECT c.id as id, c.name as name, c.business_id as businessId, b.name as businessName " +
                          "FROM campaigns c " +
                          "JOIN businesses b ON c.business_id = b.id " +






                          "WHERE c.id = :id", nativeQuery = true)
           Optional<PublicCampaignProjection> findPublicById(@Param("id") UUID id);
     }
