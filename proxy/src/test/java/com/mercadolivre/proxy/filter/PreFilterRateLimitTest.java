package com.mercadolivre.proxy.filter;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import com.mercadolivre.proxy.service.limiter.RateLimitService;
import com.mercadolivre.proxy.service.sliding.IpRateLimitImpl;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.monitoring.CounterFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.netflix.zuul.metrics.EmptyCounterFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class PreFilterRateLimitTest {

    @Mock
    RateLimitService rateLimitService;

    @Mock
    LimiterRateProperties properties;

    @Mock
    private RequestAttributes requestAttributes;

    @InjectMocks
    private PreFilterRateLimit filter;

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    private RequestContext context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RequestContext.testSetCurrentContext(new RequestContext());
        CounterFactory.initialize(new EmptyCounterFactory());
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        this.context = new RequestContext();
        RequestContext.testSetCurrentContext(this.context);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        this.context.clear();
        this.context.setRequest(this.request);
        this.context.setResponse(this.response);
    }

    @Test
    public void run() {
        Object actual = filter.run();
        assertNull(actual);
    }
}