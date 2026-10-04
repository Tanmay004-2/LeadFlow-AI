package com.leadflow.sales.service;

import com.leadflow.chat.entity.Conversation;
import com.leadflow.chat.repository.ConversationRepository;
import com.leadflow.sales.entity.AssignmentSettings;
import com.leadflow.sales.repository.AssignmentSettingsRepository;
import com.leadflow.user.entity.Role;
import com.leadflow.user.entity.User;
import com.leadflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentAssignmentService {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final AssignmentSettingsRepository settingsRepository;

    public void assignConversation(Conversation conversation, UUID businessId) {
        List<User> eligibleAgents = userRepository.findAll().stream()
                .filter(u -> businessId.equals(u.getBusinessId()) &&
                        (u.getRole() == Role.SALES_AGENT || u.getRole() == Role.BUSINESS_MANAGER))
                .collect(Collectors.toList());
        if (eligibleAgents.isEmpty()) return;

        AssignmentSettings settings = settingsRepository.findAll().stream().findFirst().orElse(new AssignmentSettings());
        User selectedAgent;
        if ("LEAST_WORKLOAD".equals(settings.getStrategy())) {
            selectedAgent = eligibleAgents.stream()
                    .min(Comparator.comparingInt(agent ->
                            conversationRepository.countByAgentIdAndStatus(agent.getId(), "HANDED_OFF")))
                    .orElse(eligibleAgents.get(0));
        } else {
            if (settings.getLastAssignedAgentId() == null) {
                selectedAgent = eligibleAgents.get(0);
            } else {
                int lastIndex = -1;
                for (int i = 0; i < eligibleAgents.size(); i++) {
                    if (eligibleAgents.get(i).getId().equals(settings.getLastAssignedAgentId())) {
                        lastIndex = i;
                        break;
                    }
                }
                selectedAgent = eligibleAgents.get((lastIndex + 1) % eligibleAgents.size());
            }
            settings.setLastAssignedAgentId(selectedAgent.getId());
            settingsRepository.save(settings);
        }
        conversation.setAgentId(selectedAgent.getId());
        conversation.setStatus("HANDED_OFF");
        conversationRepository.save(conversation);
    }
}
