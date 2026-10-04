package com.leadflow.auth;

     import com.leadflow.core.api.ApiResponse;
     import com.leadflow.security.JwtService;
     import com.leadflow.user.entity.User;
     import com.leadflow.user.repository.UserRepository;
     import lombok.Data;
     import lombok.RequiredArgsConstructor;
     import org.springframework.http.ResponseEntity;
     import org.springframework.security.crypto.password.PasswordEncoder;
     import org.springframework.web.bind.annotation.PostMapping;
     import org.springframework.web.bind.annotation.RequestBody;
     import org.springframework.web.bind.annotation.RequestMapping;
     import org.springframework.web.bind.annotation.RestController;

     @RestController
     @RequestMapping("/api/v1/auth")
     @RequiredArgsConstructor
     public class AuthController {

           private final UserRepository userRepository;
           private final PasswordEncoder passwordEncoder;
           private final JwtService jwtService;

           @PostMapping("/login")
           public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
               User user = userRepository.findByEmail(request.getEmail())
                       .orElseThrow(() -> new SecurityException("Invalid credentials"));

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    throw new SecurityException("Invalid credentials");
                }






                String token = jwtService.generateToken(user);
                return ResponseEntity.ok(ApiResponse.success(token, "Login successful"));
           }

           @Data
           public static class LoginRequest {
               private String email;
               private String password;
           }
     }
