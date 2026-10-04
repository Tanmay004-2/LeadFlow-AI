package com.leadflow.chat.service;

     import com.leadflow.chat.entity.Conversation;
     import com.leadflow.chat.entity.Lead;
     import com.leadflow.chat.entity.Message;
     import com.leadflow.chat.entity.SenderType;
     import com.leadflow.chat.repository.ConversationRepository;
     import com.leadflow.chat.repository.LeadRepository;
     import com.leadflow.chat.repository.MessageRepository;
     import com.leadflow.core.tenant.TenantContext;
     import com.leadflow.knowledge.repository.BusinessKnowledgeRepository;
     import com.leadflow.nurture.entity.FollowUpStatus;
     import com.leadflow.nurture.entity.FollowUpTask;
     import com.leadflow.nurture.repository.FollowUpTaskRepository;
     import com.leadflow.nurture.service.FollowUpService;
     import com.leadflow.sales.service.AgentAssignmentService;
     import com.leadflow.scoring.entity.LeadScoreHistory;
     import com.leadflow.scoring.repository.LeadScoreHistoryRepository;
     import com.leadflow.scoring.repository.ScoringRuleRepository;
     import lombok.RequiredArgsConstructor;
     import org.springframework.stereotype.Service;
     import org.springframework.transaction.annotation.Transactional;







     import java.time.LocalDateTime;
     import java.util.HashMap;
     import java.util.List;
     import java.util.Map;
     import java.util.UUID;

     @Service
     @RequiredArgsConstructor
     public class ChatService {

           private final ConversationRepository conversationRepository;
           private final LeadRepository leadRepository;
           private final MessageRepository messageRepository;
           private final FollowUpTaskRepository followUpTaskRepository;
           private final BusinessKnowledgeRepository knowledgeRepository;
           private final ScoringRuleRepository scoringRuleRepository;
           private final LeadScoreHistoryRepository historyRepository;
           private final AiServiceClient aiServiceClient;
           private final AgentAssignmentService agentAssignmentService;
           private final FollowUpService followUpService;

           @Transactional
           public Message processCustomerMessage(UUID conversationId, String content, UUID jwtSessionId) {
               Lead authLead = leadRepository.findBySessionId(jwtSessionId)
                       .orElseThrow(() -> new SecurityException("Invalid session context"));

                Conversation conversation = conversationRepository.findById(conversationId)
                        .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

                if (!conversation.getLeadId().equals(authLead.getId())) {
                    throw new SecurityException("Unauthorized conversation access");
                }

                if ("HANDED_OFF".equals(conversation.getStatus())) {
                    throw new IllegalStateException("Conversation is currently managed by a human agent.");
                }

                // Cancel pending follow-ups
                List<FollowUpTask> pending = followUpTaskRepository.findByConversationIdAndStatus(conversationId, FollowUpStatus.PENDING);
                pending.forEach(t -> t.setStatus(FollowUpStatus.CANCELLED));
                followUpTaskRepository.saveAll(pending);

                // Save Customer Message
                Message customerMsg = new Message();
                customerMsg.setConversationId(conversationId);
                customerMsg.setSenderType(SenderType.CUSTOMER);
                customerMsg.setContent(content);
                messageRepository.save(customerMsg);

                // Fetch Context
                List<Map<String, Object>> knowledge = knowledgeRepository.findByIsActiveTrue().stream()
                        .map(k -> Map.of("type", (Object)k.getType().name(), "content", (Object)k.getContent()))






                        .toList();
                List<Map<String, Object>> rules = scoringRuleRepository.findAll().stream()
                        .map(r -> Map.of("rule_type", (Object)r.getRuleType(), "condition_value", r.getConditionValue(), "score_delta", r.getScoreDelta()))
                        .toList();

                // Call AI
                int oldScore = authLead.getScore() != null ? authLead.getScore() : 0;
                AiServiceClient.AiResponse aiResult = aiServiceClient.processMessage(content, oldScore, knowledge, rules);

                // Score & Qualification Update
                int newScore = aiResult.getNewScore();
                if (newScore != oldScore) {
                    LeadScoreHistory history = new LeadScoreHistory();
                    history.setLeadId(authLead.getId());
                    history.setOldScore(oldScore);
                    history.setNewScore(newScore);
                    history.setReason("AI Evaluation: " + aiResult.getIntent());
                    historyRepository.save(history);
                }

                authLead.setScore(newScore);
                if (authLead.getExtractedData() == null) authLead.setExtractedData(new HashMap<>());
                authLead.getExtractedData().putAll(aiResult.getExtractedEntities());

                if (newScore >= 81) authLead.setStatus("HIGH_INTENT");
                else if (newScore >= 61) authLead.setStatus("HOT");
                else if (newScore >= 31) authLead.setStatus("WARM");
                else authLead.setStatus("COLD");
                leadRepository.save(authLead);

                // Handle Handoff / Booking / Hesitation
                if (aiResult.isRequiresHandoff()) {
                    agentAssignmentService.assignConversation(conversation, TenantContext.getTenantId());
                    return saveAiResponse(conversationId, "I'm transferring you to a human agent right now. Please hold on a moment.");
                }
                if ("BOOK_APPOINTMENT".equals(aiResult.getAction())) {
                    return saveAiResponse(conversationId, "I can help you schedule that! Please click [here](/book) to select a time slot on our calendar.");
                }
                if ("HESITATION".equals(aiResult.getIntent())) {
                    followUpService.scheduleFollowUp(conversationId, LocalDateTime.now().plusHours(24));
                }

                return saveAiResponse(conversationId, aiResult.getResponseMessage());
           }

           private Message saveAiResponse(UUID conversationId, String content) {
               Message aiResponseMsg = new Message();
               aiResponseMsg.setConversationId(conversationId);
               aiResponseMsg.setSenderType(SenderType.AI);
               aiResponseMsg.setContent(content);
               return messageRepository.save(aiResponseMsg);
           }
     }
