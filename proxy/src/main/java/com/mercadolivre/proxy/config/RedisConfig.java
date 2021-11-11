package com.mercadolivre.proxy.config;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<String, RateLimiterModel> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RateLimiterModel> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    RedisTemplate<String, Long> rateLimitRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> longRedisTemplate = new RedisTemplate<>();
        longRedisTemplate.setConnectionFactory(connectionFactory);
        return longRedisTemplate;
    }

}