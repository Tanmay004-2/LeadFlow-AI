package com.leadflow.scoring.repository;

import com.leadflow.scoring.entity.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ScoringRuleRepository extends JpaRepository<ScoringRule, UUID> {}
