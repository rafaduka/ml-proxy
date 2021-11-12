package com.mercadolivre.proxy.service.limiter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Sets;
import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import com.mercadolivre.proxy.service.cache.CacheService;
import com.mercadolivre.proxy.service.sliding.IpRateLimitImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.TestPropertySource;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations="classpath:application-test.yaml")
public class RateLimitServiceImplTest {

    @Mock
    CacheService cacheService;

    @Mock
    LimiterRateProperties properties;

    @InjectMocks
    private RateLimitServiceImpl service;

    @Test
    public void when_ratelimit_allowed_ip_with_cache() {

        RateLimiterModel limiterModel = RateLimiterModel.builder()
                .ip("127.0.0.1")
                .path("/somePath")
                .windowInSeconds(60)
                .maxRequestsInWindow(5)
                .timeBetweenCalls(1)
                .build();

        when(cacheService.getBoundHash(limiterModel.getIp())).thenReturn(limiterModel);
        when(cacheService.boundZSetOps(limiterModel.getIp())).thenReturn(stubBoundZSetOperations());

        boolean actual = service.isAllowed(limiterModel, new IpRateLimitImpl(properties));

        assertTrue(actual);
    }

    @Test
    public void when_ratelimit_allowed_ip_without_cache() {

        RateLimiterModel limiterModel = RateLimiterModel.builder()
                .ip("127.0.0.1")
                .path("/somePath")
                .windowInSeconds(60)
                .maxRequestsInWindow(5)
                .timeBetweenCalls(1)
                .build();

        when(cacheService.getBoundHash(limiterModel.getIp())).thenReturn(null);
        when(cacheService.boundZSetOps(limiterModel.getIp())).thenReturn(stubBoundZSetOperations());


        boolean actual = service.isAllowed(limiterModel, new IpRateLimitImpl(properties));

        assertTrue(actual);
    }

    @Test
    public void when_ratelimit_not_allowed_ip_without_cache() {

        RateLimiterModel limiterModel = RateLimiterModel.builder()
                .ip("127.0.0.1")
                .path("/somePath")
                .windowInSeconds(60)
                .maxRequestsInWindow(5)
                .timeBetweenCalls(1)
                .build();

        when(cacheService.getBoundHash(limiterModel.getIp())).thenReturn(null);
        when(cacheService.boundZSetOps(limiterModel.getIp())).thenReturn(null);

        boolean actual = service.isAllowed(limiterModel, new IpRateLimitImpl(properties));

        assertFalse(actual);
    }


    public static BoundZSetOperations<String, Long> stubBoundZSetOperations() {
        return new BoundZSetOperations<String, Long>() {
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public DataType getType() {
                return null;
            }

            @Override
            public Long getExpire() {
                return null;
            }

            @Override
            public Boolean expire(long l, TimeUnit timeUnit) {
                return null;
            }

            @Override
            public Boolean expireAt(Date date) {
                return null;
            }

            @Override
            public Boolean persist() {
                return null;
            }

            @Override
            public void rename(String s) {

            }

            @Override
            public Boolean add(Long aLong, double v1) {
                return null;
            }

            @Override
            public Long add(Set<ZSetOperations.TypedTuple<Long>> set) {
                return null;
            }

            @Override
            public Long remove(Object... objects) {
                return null;
            }

            @Override
            public Double incrementScore(Long aLong, double v1) {
                return null;
            }

            @Override
            public Long rank(Object o) {
                return null;
            }

            @Override
            public Long reverseRank(Object o) {
                return null;
            }

            @Override
            public Set<Long> range(long l, long l1) {
                return null;
            }

            @Override
            public Set<ZSetOperations.TypedTuple<Long>> rangeWithScores(long l, long l1) {
                return null;
            }

            @Override
            public Set<Long> rangeByScore(double v, double v1) {
                return Sets.newHashSet();
            }

            @Override
            public Set<ZSetOperations.TypedTuple<Long>> rangeByScoreWithScores(double v, double v1) {
                return null;
            }

            @Override
            public Set<Long> reverseRange(long l, long l1) {
                return null;
            }

            @Override
            public Set<ZSetOperations.TypedTuple<Long>> reverseRangeWithScores(long l, long l1) {
                return null;
            }

            @Override
            public Set<Long> reverseRangeByScore(double v, double v1) {
                return null;
            }

            @Override
            public Set<ZSetOperations.TypedTuple<Long>> reverseRangeByScoreWithScores(double v, double v1) {
                return null;
            }

            @Override
            public Long count(double v, double v1) {
                return null;
            }

            @Override
            public Long size() {
                return null;
            }

            @Override
            public Long zCard() {
                return null;
            }

            @Override
            public Double score(Object o) {
                return null;
            }

            @Override
            public Long removeRange(long l, long l1) {
                return null;
            }

            @Override
            public Long removeRangeByScore(double v, double v1) {
                return null;
            }

            @Override
            public Long unionAndStore(String s, String k1) {
                return null;
            }

            @Override
            public Long unionAndStore(Collection<String> collection, String s) {
                return null;
            }

            @Override
            public Long unionAndStore(Collection<String> collection, String s, RedisZSetCommands.Aggregate aggregate) {
                return null;
            }

            @Override
            public Long unionAndStore(Collection<String> collection, String s, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
                return null;
            }

            @Override
            public Long intersectAndStore(String s, String k1) {
                return null;
            }

            @Override
            public Long intersectAndStore(Collection<String> collection, String s) {
                return null;
            }

            @Override
            public Long intersectAndStore(Collection<String> collection, String s, RedisZSetCommands.Aggregate aggregate) {
                return null;
            }

            @Override
            public Long intersectAndStore(Collection<String> collection, String s, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
                return null;
            }

            @Override
            public Cursor<ZSetOperations.TypedTuple<Long>> scan(ScanOptions scanOptions) {
                return null;
            }

            @Override
            public Set<Long> rangeByLex(RedisZSetCommands.Range range) {
                return null;
            }

            @Override
            public Set<Long> rangeByLex(RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
                return null;
            }

            @Override
            public RedisOperations<String, Long> getOperations() {
                return null;
            }
        };
    }
}