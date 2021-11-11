package com.mercadolivre.proxy.service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private RedisTemplate<String, RateLimiterModel> redisTemplate;
    private RedisTemplate<String, Long> rateLimitRedisTemplate;

    public RateLimitServiceImpl(RedisTemplate<String, RateLimiterModel> redisTemplate, RedisTemplate<String, Long> rateLimitRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.rateLimitRedisTemplate = rateLimitRedisTemplate;
    }

    @Override
    public boolean isAllowed(RateLimiterModel rateLimiterModel, SlidingWindowLimitable rateLimitSlidingWindow) {
        boolean isAllowed = false;
        String key = rateLimitSlidingWindow.getKey(rateLimiterModel);
        BoundHashOperations<String, String, RateLimiterModel> hashOps = redisTemplate.boundHashOps(key);
        RateLimiterModel limiterModel = hashOps.get(key);

        if (limiterModel == null) {
            limiterModel = RateLimiterModel.builder()
                    .ip(rateLimiterModel.getIp())
                    .path(rateLimiterModel.getPath())
                    .windowInSeconds(rateLimitSlidingWindow.windowInSeconds())
                    .maxRequestsInWindow(rateLimitSlidingWindow.maxRequestsInWindow())
                    .timeBetweenCalls(1)
                    .build();

            hashOps.put(key, limiterModel);
        }

        BoundZSetOperations<String, Long> requestZSetOps = rateLimitRedisTemplate.boundZSetOps(key);


        if (requestZSetOps == null) {
            isAllowed = false;
        }

        long timeWindowInMilli = TimeUnit.MILLISECONDS.convert(rateLimitSlidingWindow.windowInSeconds(), TimeUnit.SECONDS);
        long currentTime = Instant.now().toEpochMilli();
        long currentWindowStartTime = currentTime - timeWindowInMilli;

        requestZSetOps.removeRangeByScore(0.0, (double) currentWindowStartTime);
        int requestSize = requestZSetOps.rangeByScore(currentWindowStartTime, currentTime).size();

        if ((requestSize + 1) <= rateLimitSlidingWindow.maxRequestsInWindow()) {
            isAllowed = true;
            requestZSetOps.add(currentTime, currentTime);
        }

        if (!isAllowed) {
            isAllowed = false;
        }

        return isAllowed;
    }


}
