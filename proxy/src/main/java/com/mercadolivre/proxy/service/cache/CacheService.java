package com.mercadolivre.proxy.service.cache;

import org.springframework.data.redis.core.BoundZSetOperations;

/**
 * Provides an interface to diverse cache implementation
 * @param <T> the object to be used as value on cache
 *
 * @author Rafael Horácio
 * @date 12/11/2021
 */
public interface CacheService<T> {

    /**
     * Retrieve a value from cache given a key
     * @param key Redis key
     * @return cache value by key
     */
    T get(String key);

    /**
     * Insert a record in cache
     * @param key Redis key
     */
    void put(String key);

    /**
     * Gets hash value from cache
     * @param key Redis key
     * @return Returns the operations performed on hash values bound to the given key.
     */
    T getBoundHash(String key);

    /**
     * Insert hash value record in cache
     * @param key Redis key
     * @param t
     */
    void putBoundHash(String key, T t);

    /**
     * ZSet (or SortedSet) operations bound to a certain key.
     * @param key Redis key
     * @return Returns the operations performed on zset values (also known as sorted sets) bound to the given key.
     */
    BoundZSetOperations<String, Long> boundZSetOps(String key);
}
