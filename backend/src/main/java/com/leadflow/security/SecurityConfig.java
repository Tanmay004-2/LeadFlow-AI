package com.leadflow.security;

     import lombok.RequiredArgsConstructor;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
     import org.springframework.security.config.annotation.web.builders.HttpSecurity;
     import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
     import org.springframework.security.config.http.SessionCreationPolicy;
     import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
     import org.springframework.security.crypto.password.PasswordEncoder;
     import org.springframework.security.web.SecurityFilterChain;
     import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

     @Configuration
     @EnableWebSecurity
     @EnableMethodSecurity
     @RequiredArgsConstructor
     public class SecurityConfig {

           private final JwtAuthenticationFilter jwtAuthFilter;

           @Bean
           public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
               http
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))





                     .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/api/v1/auth/**").permitAll()
                         .requestMatchers("/api/v1/public/**").permitAll()
                         .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                         .requestMatchers("/api/v1/admin/**").hasRole("PLATFORM_ADMIN")
                         .requestMatchers("/api/v1/businesses/**").hasAnyRole("ACCOUNT_OWNER", "BUSINESS_MANAGER", "SALES_AGENT")
                         .anyRequest().authenticated()
                     )
                     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
           }

           @Bean
           public PasswordEncoder passwordEncoder() {
               return new BCryptPasswordEncoder();
           }
     }
