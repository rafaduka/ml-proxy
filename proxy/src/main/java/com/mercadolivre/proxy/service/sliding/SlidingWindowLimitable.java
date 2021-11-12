package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.stereotype.Service;

/**
 * Contract for Control Variation for the Sliding Window Algorithm
 *
 * @author Rafael Horácio
 * @date 12/11/2021
 */
@Service
public interface SlidingWindowLimitable {

    /**
     * Retrieve key according to implementation, can be IP, Path, IP+Path
     *
     * For example if it is IP, the key is 127.0.0.1.
     * If it's PATH, it's /somepath
     *
     * @param model Property values ​​that can be built in through application.yaml or via docker-compose
     * @return Redis/Cache key
     */
    String getKey(RateLimiterModel model);

    /**
     * window size in seconds
     *
     * @return window total in seconds
     */
    int windowInSeconds();

    /**
     * Maximum total request in the configured window
     *
     * @return Maximum number of requests
     */
    int maxRequestsInWindow();

}
