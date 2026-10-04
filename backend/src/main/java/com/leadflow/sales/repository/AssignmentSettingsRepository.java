package com.leadflow.sales.repository;

import com.leadflow.sales.entity.AssignmentSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssignmentSettingsRepository extends JpaRepository<AssignmentSettings, UUID> {}
