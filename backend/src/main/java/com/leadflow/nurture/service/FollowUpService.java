package com.leadflow.nurture.service;

import com.leadflow.chat.entity.Message;
import com.leadflow.chat.entity.SenderType;
import com.leadflow.chat.repository.MessageRepository;
import com.leadflow.core.tenant.TenantContext;
import com.leadflow.nurture.entity.FollowUpStatus;
import com.leadflow.nurture.entity.FollowUpTask;
import com.leadflow.nurture.repository.FollowUpTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowUpService {
    private final FollowUpTaskRepository taskRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void scheduleFollowUp(UUID conversationId, LocalDateTime scheduledFor) {
        List<FollowUpTask> existing = taskRepository.findByConversationIdAndStatus(conversationId, FollowUpStatus.PENDING);
        existing.forEach(t -> t.setStatus(FollowUpStatus.CANCELLED));
        taskRepository.saveAll(existing);

        FollowUpTask task = new FollowUpTask();
        task.setConversationId(conversationId);
        task.setScheduledFor(scheduledFor);
        taskRepository.save(task);
        log.info("Scheduled follow up for conversation {} at {}", conversationId, scheduledFor);
    }

    @Scheduled(fixedRate = 60000)
    public void processDueFollowUps() {
        for (FollowUpTask task : taskRepository.findDueTasks(LocalDateTime.now())) {
            executeTaskSecurely(task);
        }
    }

    @Transactional
    protected void executeTaskSecurely(FollowUpTask task) {
        TenantContext.setTenantId(task.getBusinessId());
        try {
            FollowUpTask managedTask = taskRepository.findById(task.getId()).orElse(null);
            if (managedTask == null || managedTask.getStatus() != FollowUpStatus.PENDING) return;

            managedTask.setStatus(FollowUpStatus.PROCESSING);

            Message followUpMsg = new Message();
            followUpMsg.setConversationId(managedTask.getConversationId());
            followUpMsg.setSenderType(SenderType.AI);
            followUpMsg.setContent("Hi! I just wanted to check in. Do you have any questions I can help answer?");
            messageRepository.save(followUpMsg);

            managedTask.setStatus(FollowUpStatus.COMPLETED);
            taskRepository.save(managedTask);
        } catch (Exception e) {
            log.error("Failed to execute follow up task {}", task.getId(), e);
        } finally {
            TenantContext.clear();
        }
    }
}
