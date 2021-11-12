package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import org.springframework.stereotype.Service;

@Service
public class PathRateLimitImpl implements SlidingWindowLimitable {

    private static final int WINDOW_IN_SECONDS = 10;
    private static final int MAX_REQUEST_IN_WINDOW = 5;
    private LimiterRateProperties properties;

    public PathRateLimitImpl() {
    }

    public PathRateLimitImpl(LimiterRateProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getKey(RateLimiterModel model) {
        return model.getPath();
    }

    @Override
    public int windowInSeconds() {
        if (properties.getPathInterval() != 0) {
            return properties.getPathInterval();
        }
        return WINDOW_IN_SECONDS;
    }

    @Override
    public int maxRequestsInWindow() {
        if (properties.getPathMaxRequest() != 0) {
            return properties.getPathMaxRequest();
        }
        return MAX_REQUEST_IN_WINDOW;
    }

}
