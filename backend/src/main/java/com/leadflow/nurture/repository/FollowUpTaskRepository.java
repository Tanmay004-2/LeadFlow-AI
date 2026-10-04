package com.leadflow.nurture.repository;

import com.leadflow.nurture.entity.FollowUpTask;
import com.leadflow.nurture.entity.FollowUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FollowUpTaskRepository extends JpaRepository<FollowUpTask, UUID> {
    @Query(value = "SELECT * FROM follow_up_tasks WHERE status = 'PENDING' AND scheduled_for <= :now", nativeQuery = true)
    List<FollowUpTask> findDueTasks(LocalDateTime now);
    List<FollowUpTask> findByConversationIdAndStatus(UUID conversationId, FollowUpStatus status);
}
