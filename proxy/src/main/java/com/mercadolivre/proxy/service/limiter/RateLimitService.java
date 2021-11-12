package com.mercadolivre.proxy.service.limiter;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import org.springframework.stereotype.Service;

/**
 * Interface to provide rate limit implementations
 *
 * @author Rafael Horácio
 * @date 12/11/2021
 */
@Service
public interface RateLimitService {
    /**
     * Method that contains the logic for blocking web requests
     *
     * @param rateLimiterModel model with interval data and blocking window
     * @param rateLimitSlidingWindow Contract to know the variation of implementations and control of the algorithm.
     *                               IP, Path and IP + Path are currently provided
     * @return
     */
    boolean isAllowed(RateLimiterModel rateLimiterModel, SlidingWindowLimitable rateLimitSlidingWindow);
}
