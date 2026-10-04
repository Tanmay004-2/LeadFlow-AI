package com.leadflow.security;

     import com.leadflow.core.tenant.TenantContext;
     import com.leadflow.user.entity.Role;
     import io.jsonwebtoken.Claims;
     import jakarta.servlet.FilterChain;





     import jakarta.servlet.ServletException;
     import jakarta.servlet.http.HttpServletRequest;
     import jakarta.servlet.http.HttpServletResponse;
     import lombok.RequiredArgsConstructor;
     import org.springframework.lang.NonNull;
     import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
     import org.springframework.security.core.context.SecurityContextHolder;
     import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
     import org.springframework.stereotype.Component;
     import org.springframework.web.filter.OncePerRequestFilter;

     import java.io.IOException;
     import java.util.UUID;

     @Component
     @RequiredArgsConstructor
     public class JwtAuthenticationFilter extends OncePerRequestFilter {

           private final JwtService jwtService;

           @Override
           protected void doFilterInternal(
                   @NonNull HttpServletRequest request,
                   @NonNull HttpServletResponse response,
                   @NonNull FilterChain filterChain
           ) throws ServletException, IOException {
               final String authHeader = request.getHeader("Authorization");
               final String jwt;

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                jwt = authHeader.substring(7);

                try {
                    if (jwtService.isTokenValid(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
                        Claims claims = jwtService.extractAllClaims(jwt);

                           String roleStr = claims.get("role", String.class);
                           UUID userId = UUID.fromString(claims.get("userId", String.class));

                           UUID accountId = claims.get("accountId") != null ? UUID.fromString(claims.get("accountId", String.class)) : null;
                           UUID businessId = claims.get("businessId") != null ? UUID.fromString(claims.get("businessId", String.class)) : null;

                           AuthUser authUser = AuthUser.builder()
                                   .id(userId)
                                   .email(claims.getSubject())
                                   .role(Role.valueOf(roleStr))
                                   .accountId(accountId)
                                   .businessId(businessId)
                                   .build();







                           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                   authUser,
                                   null,
                                   authUser.getAuthorities()
                           );
                           authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                           SecurityContextHolder.getContext().setAuthentication(authToken);

                           if (businessId != null && !Role.PLATFORM_ADMIN.name().equals(roleStr)) {
                               TenantContext.setTenantId(businessId);
                           }
                    }
                } catch (Exception e) {
                    // Invalid token, do not authenticate
                }

                try {
                    filterChain.doFilter(request, response);
                } finally {
                    TenantContext.clear();
                }
           }
     }
