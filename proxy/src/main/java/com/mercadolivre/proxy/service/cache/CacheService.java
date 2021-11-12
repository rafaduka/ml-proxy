package com.mercadolivre.proxy.service.cache;

import org.springframework.data.redis.core.BoundZSetOperations;

public interface CacheService<T> {
    T get(String key);
    void put(String key);
    T getBoundHash(String key);
    void putBoundHash(String key, T t);
    BoundZSetOperations<String, Long> boundZSetOps(String key);
}
