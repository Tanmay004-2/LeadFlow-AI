package com.leadflow.subscription.interceptor;

import com.leadflow.core.exception.SubscriptionExpiredException;
import com.leadflow.security.AuthUser;
import com.leadflow.subscription.service.SubscriptionService;
import com.leadflow.user.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements HandlerInterceptor {
    private final SubscriptionService subscriptionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthUser user) {
            if (Role.PLATFORM_ADMIN.equals(user.getRole())) return true;
            if (user.getAccountId() != null && !subscriptionService.hasActiveAccess(user.getAccountId())) {
                throw new SubscriptionExpiredException("Your account subscription has expired or is past due. Please update your billing information.");
            }
        }
        return true;
    }
}
