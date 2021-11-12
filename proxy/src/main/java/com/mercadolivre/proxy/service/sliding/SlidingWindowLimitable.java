package com.mercadolivre.proxy.service.sliding;

import com.mercadolivre.proxy.model.RateLimiterModel;
import org.springframework.stereotype.Service;

/**
 * Contrato para variação de controle para o algoritmo Sliding Window
 *
 * @author Rafael Horácio
 * @date 12/11/2021
 */
@Service
public interface SlidingWindowLimitable {

    /**
     * Retrieve key according to implementation, can be IP, Path, IP+Path
     *
     * @param model
     * @return
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
