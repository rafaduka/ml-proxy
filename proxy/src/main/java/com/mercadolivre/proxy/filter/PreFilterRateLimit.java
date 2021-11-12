package com.mercadolivre.proxy.filter;

import com.mercadolivre.proxy.model.RateLimiterModel;
import com.mercadolivre.proxy.properties.LimiterRateProperties;
import com.mercadolivre.proxy.service.limiter.RateLimitService;
import com.mercadolivre.proxy.service.sliding.IpAndPathRateLimitImpl;
import com.mercadolivre.proxy.service.sliding.IpRateLimitImpl;
import com.mercadolivre.proxy.service.sliding.PathRateLimitImpl;
import com.mercadolivre.proxy.service.sliding.SlidingWindowLimitable;
import com.mercadolivre.proxy.util.RequestUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class PreFilterRateLimit extends ZuulFilter {

    private final RateLimitService rateLimitService;
    private final LimiterRateProperties properties;

    public PreFilterRateLimit(RateLimitService rateLimitService, LimiterRateProperties properties) {
        this.rateLimitService = rateLimitService;
        this.properties = properties;
    }

    @Override
    public Object run() {
        final Context context = Context.getInstance();
        context.setRequest(RequestContext.getCurrentContext().getRequest());

        context.setDuration(System.currentTimeMillis());

        RequestContext ctx = RequestContext.getCurrentContext();

        boolean isIpAllowed = rateLimit(ctx, new IpRateLimitImpl(properties));
        boolean isPathAllowed = rateLimit(ctx, new PathRateLimitImpl(properties));
        boolean isIpAndPathAllowed = rateLimit(ctx, new IpAndPathRateLimitImpl(properties));

        if (hasSomeLimitExceeded(isIpAllowed, isPathAllowed, isIpAndPathAllowed)) {
            ctx.getRequest().setAttribute("hasLimitExceeded", Boolean.TRUE);
        }

        return null;
    }

    private boolean rateLimit(RequestContext ctx, SlidingWindowLimitable strategy) {

        String ip = RequestUtils.getClientIp(ctx.getRequest());
        String path = ctx.getRequest().getRequestURI();

        RateLimiterModel model = RateLimiterModel.builder()
                .ip(ip)
                .path(path)
                .build();

        return rateLimitService.isAllowed(model, strategy);
    }

    private static boolean hasSomeLimitExceeded(boolean isIpAllowed, boolean isPathAllowed, boolean isIpAndPathAllowed) {
        return isIpAllowed == false || isPathAllowed == false || isIpAndPathAllowed == false;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
