package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.stereotype.Service;

@Service
public interface SlidingWindowLimitable {

    String getKey(RateLimiterModel model);
    int windowInSeconds();
    int maxRequestsInWindow();

}
