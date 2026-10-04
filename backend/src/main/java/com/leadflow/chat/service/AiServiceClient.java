package com.leadflow.chat.service;

     import lombok.Data;
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.stereotype.Service;





     import org.springframework.web.client.RestTemplate;

     import java.util.List;
     import java.util.Map;

     @Service
     public class AiServiceClient {

           @Value("${app.ai-service.url:http://localhost:8000}")
           private String aiServiceUrl;

           private final RestTemplate restTemplate = new RestTemplate();

           public AiResponse processMessage(String content, int currentScore, List<Map<String, Object>> knowledge, List<Map<String, Object>> rules) {
               AiRequest request = new AiRequest();
               request.setContent(content);
               request.setCurrentScore(currentScore);
               request.setKnowledge(knowledge);
               request.setScoringRules(rules);

                return restTemplate.postForObject(aiServiceUrl + "/api/v1/ai/process", request, AiResponse.class);
           }

           @Data
           public static class AiRequest {
               private String content;
               private int currentScore;
               private List<Map<String, Object>> knowledge;
               private List<Map<String, Object>> scoringRules;
           }

           @Data
           public static class AiResponse {
               private String intent;
               private Map<String, Object> extractedEntities;
               private int newScore;
               private String responseMessage;
               private boolean requiresHandoff;
               private String action;
           }
     }
