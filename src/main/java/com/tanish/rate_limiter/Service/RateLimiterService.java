package com.tanish.rate_limiter.Service;

import com.tanish.rate_limiter.model.RateLimitResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private static final int REQUEST_LIMIT = 5;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public RateLimitResult checkLimit(String userId) {

        long currentMinute = Instant.now().getEpochSecond() / 60;

        String key = "rate_limit:" + userId + ":" + currentMinute;

        Long requestCount = redisTemplate.opsForValue().increment(key);

        if (requestCount == null) {
            requestCount = 0L;
        }

        if (requestCount == 1) {
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        }

        long remaining = REQUEST_LIMIT - requestCount;

        boolean allowed = requestCount <= REQUEST_LIMIT;

        if (remaining < 0) {
            remaining = 0;
        }

        return new RateLimitResult(allowed, remaining, REQUEST_LIMIT);
    }
}