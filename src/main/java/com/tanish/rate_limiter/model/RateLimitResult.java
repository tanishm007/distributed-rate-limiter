package com.tanish.rate_limiter.model;

public class RateLimitResult {

    private boolean allowed;
    private long remaining;
    private long limit;

    public RateLimitResult(boolean allowed, long remaining, long limit) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.limit = limit;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public long getRemaining() {
        return remaining;
    }

    public long getLimit() {
        return limit;
    }
}