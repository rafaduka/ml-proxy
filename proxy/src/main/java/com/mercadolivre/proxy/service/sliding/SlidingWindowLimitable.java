package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;

public interface SlidingWindowLimitable {

    String getKey(RateLimiterModel model);
    int windowInSeconds();
    int maxRequestsInWindow();

}
