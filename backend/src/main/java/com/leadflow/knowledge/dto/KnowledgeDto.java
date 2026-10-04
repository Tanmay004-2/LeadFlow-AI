package com.leadflow.knowledge.dto;

import com.leadflow.knowledge.entity.KnowledgeType;
import lombok.Data;
import java.util.UUID;

@Data
public class KnowledgeDto {
    private UUID id;
    private UUID businessId;
    private KnowledgeType type;
    private String content;
    private Boolean isActive;
}
