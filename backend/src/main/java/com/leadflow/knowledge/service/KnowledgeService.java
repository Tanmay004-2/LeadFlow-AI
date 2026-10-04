package com.leadflow.knowledge.service;

     import com.leadflow.knowledge.dto.KnowledgeDto;
     import com.leadflow.knowledge.entity.BusinessKnowledge;
     import com.leadflow.knowledge.repository.BusinessKnowledgeRepository;
     import lombok.RequiredArgsConstructor;
     import org.springframework.stereotype.Service;
     import org.springframework.transaction.annotation.Transactional;

     import java.util.List;
     import java.util.UUID;
     import java.util.stream.Collectors;

     @Service
     @RequiredArgsConstructor
     public class KnowledgeService {

           private final BusinessKnowledgeRepository knowledgeRepository;

           @Transactional(readOnly = true)
           public List<KnowledgeDto> getAllKnowledge() {
               return knowledgeRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
           }







           @Transactional
           public KnowledgeDto addKnowledge(KnowledgeDto dto) {
               BusinessKnowledge knowledge = new BusinessKnowledge();
               knowledge.setType(dto.getType());
               knowledge.setContent(dto.getContent());
               knowledge.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

                BusinessKnowledge saved = knowledgeRepository.save(knowledge);
                return mapToDto(saved);
           }

           @Transactional
           public void deleteKnowledge(UUID id) {
               knowledgeRepository.deleteById(id);
           }

           private KnowledgeDto mapToDto(BusinessKnowledge knowledge) {
               KnowledgeDto dto = new KnowledgeDto();
               dto.setId(knowledge.getId());
               dto.setBusinessId(knowledge.getBusinessId());
               dto.setType(knowledge.getType());
               dto.setContent(knowledge.getContent());
               dto.setIsActive(knowledge.getIsActive());
               return dto;
           }
     }
