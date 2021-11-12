package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import org.springframework.stereotype.Service;

@Service
public class IpAndPathRateLimitImpl implements SlidingWindowLimitable {

    private static final int WINDOW_IN_SECONDS = 10;
    private static final int MAX_REQUEST_IN_WINDOW = 5;
    private LimiterRateProperties properties;

    public IpAndPathRateLimitImpl() {
    }

    public IpAndPathRateLimitImpl(LimiterRateProperties properties) {
        this.properties = properties;
    }


    @Override
    public String getKey(RateLimiterModel model) {
        return model.getIp().concat(":").concat(model.getPath());
    }

    @Override
    public int windowInSeconds() {
        if (properties.getIpAndPathInterval() != 0) {
            return properties.getIpAndPathInterval();
        }
        return WINDOW_IN_SECONDS;
    }

    @Override
    public int maxRequestsInWindow() {
        if (properties.getIpAndPathMaxRequest() != 0) {
            return properties.getIpAndPathMaxRequest();
        }
        return MAX_REQUEST_IN_WINDOW;
    }

}
