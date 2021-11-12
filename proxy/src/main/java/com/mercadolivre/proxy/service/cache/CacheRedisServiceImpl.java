package com.mercadolivre.proxy.service.cache;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class CacheRedisServiceImpl implements CacheService<RateLimiterModel> {

    private final RedisTemplate<String, RateLimiterModel> redisTemplate;
    private final RedisTemplate<String, Long> rateLimitRedisTemplate;

    public CacheRedisServiceImpl(RedisTemplate<String, RateLimiterModel> redisTemplate, RedisTemplate<String, Long> rateLimitRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.rateLimitRedisTemplate = rateLimitRedisTemplate;
    }

    @Override
    public RateLimiterModel get(String key) {
        // Some implementation from mercado livre goes here
        // No ideas for while =)
        return null;
    }

    @Override
    public void put(String key) {
        // Some implementation from mercado livre goes here
        // No ideas for while =)
    }

    @Override
    public RateLimiterModel getBoundHash(String key) {
        BoundHashOperations<String, String, RateLimiterModel> hashOps = redisTemplate.boundHashOps(key);
        return hashOps.get(key);
    }

    @Override
    public void putBoundHash(String key, RateLimiterModel rateLimiterModel) {
        BoundHashOperations<String, String, RateLimiterModel> hashOps = redisTemplate.boundHashOps(key);
        hashOps.put(key, rateLimiterModel);
    }

    @Override
    public BoundZSetOperations<String, Long> boundZSetOps(String key) {
        return rateLimitRedisTemplate.boundZSetOps(key);
    }

}
