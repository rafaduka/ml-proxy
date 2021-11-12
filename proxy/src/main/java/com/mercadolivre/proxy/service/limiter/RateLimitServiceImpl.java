package com.mercadolivre.proxy.service.limiter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.service.cache.CacheService;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private final CacheService<RateLimiterModel> cache;

    public RateLimitServiceImpl(CacheService cache) {
        this.cache = cache;
    }

    @Override
    public boolean isAllowed(RateLimiterModel rateLimiterModel, SlidingWindowLimitable rateLimitSlidingWindow) {
        boolean isAllowed = false;
        String key = rateLimitSlidingWindow.getKey(rateLimiterModel);
        RateLimiterModel limiterModel = cache.getBoundHash(key);

        if (limiterModel == null) {
            limiterModel = RateLimiterModel.builder()
                    .ip(rateLimiterModel.getIp())
                    .path(rateLimiterModel.getPath())
                    .windowInSeconds(rateLimitSlidingWindow.windowInSeconds())
                    .maxRequestsInWindow(rateLimitSlidingWindow.maxRequestsInWindow())
                    .timeBetweenCalls(1)
                    .build();

            cache.putBoundHash(key, limiterModel);
        }

        BoundZSetOperations<String, Long> zSetOps = cache.boundZSetOps(key);

        if (zSetOps == null) {
            return false;
        }

        long timeWindowInMilli = TimeUnit.MILLISECONDS.convert(rateLimitSlidingWindow.windowInSeconds(), TimeUnit.SECONDS);
        long currentTime = Instant.now().toEpochMilli();
        long currentWindowStartTime = currentTime - timeWindowInMilli;

        zSetOps.removeRangeByScore(0.0, (double) currentWindowStartTime);
        int requestSize = zSetOps.rangeByScore(currentWindowStartTime, currentTime).size();

        if ((requestSize + 1) <= rateLimitSlidingWindow.maxRequestsInWindow()) {
            isAllowed = true;
            zSetOps.add(currentTime, currentTime);
        }

        if (!isAllowed) {
            isAllowed = false;
        }

        return isAllowed;
    }

}
