package com.mercadolivre.proxy.service.limiter;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import org.springframework.stereotype.Service;


@Service
public interface RateLimitService {
    boolean isAllowed(RateLimiterModel rateLimiterModel, SlidingWindowLimitable rateLimitSlidingWindow);
}
