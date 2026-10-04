package com.leadflow.core.exception;

     import com.leadflow.core.api.ApiResponse;
     import org.springframework.http.HttpStatus;
     import org.springframework.http.ResponseEntity;
     import org.springframework.web.bind.annotation.ExceptionHandler;
     import org.springframework.web.bind.annotation.RestControllerAdvice;

     @RestControllerAdvice
     public class GlobalExceptionHandler {

           @ExceptionHandler(SubscriptionExpiredException.class)
           public ResponseEntity<ApiResponse<Void>> handleSubscriptionExpired(SubscriptionExpiredException ex) {
               return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                       .body(ApiResponse.error(ex.getMessage(), null));
           }

           @ExceptionHandler(SecurityException.class)
           public ResponseEntity<ApiResponse<Void>> handleSecurityException(SecurityException ex) {
               return ResponseEntity.status(HttpStatus.FORBIDDEN)
                       .body(ApiResponse.error(ex.getMessage(), null));
           }

           @ExceptionHandler(IllegalArgumentException.class)
           public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)





                           .body(ApiResponse.error(ex.getMessage(), null));
           }

           @ExceptionHandler(Exception.class)
           public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(ApiResponse.error("An unexpected error occurred", null));
           }
     }
