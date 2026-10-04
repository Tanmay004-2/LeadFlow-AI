package com.leadflow.chat.controller;

     import com.leadflow.chat.entity.Message;
     import com.leadflow.chat.service.ChatService;
     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.security.AuthUser;
     import lombok.Data;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.core.annotation.AuthenticationPrincipal;
     import org.springframework.web.bind.annotation.*;

     import java.util.UUID;

     @RestController
     @RequestMapping("/api/v1/customer/chat")
     @RequiredArgsConstructor
     public class CustomerChatController {

           private final ChatService chatService;

           @PostMapping("/{conversationId}")
           public ResponseEntity<ApiResponse<Message>> sendMessage(
                   @PathVariable UUID conversationId,
                   @RequestBody MessageRequest request,
                   @AuthenticationPrincipal AuthUser authUser) {

                Message response = chatService.processCustomerMessage(conversationId, request.getContent(), authUser.getId());
                return ResponseEntity.ok(ApiResponse.success(response, "Message processed"));
           }

           @Data
           public static class MessageRequest {
               private String content;
           }
     }
