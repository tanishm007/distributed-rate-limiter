package com.tanish.rate_limiter.interceptor;

import com.tanish.rate_limiter.model.RateLimitResult;
import com.tanish.rate_limiter.Service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userId = request.getHeader("X-User-Id");

        if (userId == null || userId.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("Missing X-User-Id header");
            return false;
        }

        RateLimitResult result = rateLimiterService.checkLimit(userId);

        // Add rate limit headers
        response.setHeader("X-RateLimit-Limit", String.valueOf(result.getLimit()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(result.getRemaining()));

        if (!result.isAllowed()) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return false;
        }

        return true;
    }
}