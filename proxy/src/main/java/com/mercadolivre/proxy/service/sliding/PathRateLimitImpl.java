package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.stereotype.Service;

@Service
public class PathRateLimitImpl implements SlidingWindowLimitable {

    private static final int WINDOW_IN_SECONDS = 10;
    private static final int MAX_REQUEST_IN_WINDOW = 5;

    @Override
    public String getKey(RateLimiterModel model) {
        return model.getPath();
    }

    @Override
    public int windowInSeconds() {
        return WINDOW_IN_SECONDS;
    }

    @Override
    public int maxRequestsInWindow() {
        return MAX_REQUEST_IN_WINDOW;
    }

}
