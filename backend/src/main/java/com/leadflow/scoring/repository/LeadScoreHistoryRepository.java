package com.leadflow.scoring.repository;

import com.leadflow.scoring.entity.LeadScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LeadScoreHistoryRepository extends JpaRepository<LeadScoreHistory, UUID> {}
