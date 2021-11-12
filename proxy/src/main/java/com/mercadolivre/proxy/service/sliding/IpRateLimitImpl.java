package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import org.springframework.stereotype.Service;

@Service
public class IpRateLimitImpl implements SlidingWindowLimitable {

    private static final int WINDOW_IN_SECONDS = 10;
    private static final int MAX_REQUEST_IN_WINDOW = 5;
    private LimiterRateProperties properties;

    public IpRateLimitImpl() {
    }

    public IpRateLimitImpl(LimiterRateProperties limiterRateProperties) {
        this.properties = limiterRateProperties;
    }

    @Override
    public String getKey(RateLimiterModel model) {
        return model.getIp();
    }

    @Override
    public int windowInSeconds() {
        int windowInSeconds = properties.getIpInterval();
        if (windowInSeconds != 0) {
            return windowInSeconds;
        }
        return WINDOW_IN_SECONDS;
    }

    @Override
    public int maxRequestsInWindow() {
        if (properties.getIpInterval() != 0) {
            return properties.getIpInterval();
        }
        return MAX_REQUEST_IN_WINDOW;
    }

}
