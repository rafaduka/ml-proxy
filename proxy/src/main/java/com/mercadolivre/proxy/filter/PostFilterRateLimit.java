package com.mercadolivre.proxy.filter;

import java.util.Date;

import com.mercadolivre.proxy.entity.Request;
import com.mercadolivre.proxy.repository.RequestRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class PostFilterRateLimit extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(PostFilterRateLimit.class);
    private final RequestRepository repository;

    @Autowired
    public PostFilterRateLimit(RequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object run() {
        final Context context = Context.getInstance();
        context.setRequest(RequestContext.getCurrentContext().getRequest());
        context.setDuration(System.currentTimeMillis() - context.getDuration());
        context.setResponse(RequestContext.getCurrentContext().getResponse());
        RequestContext ctx = RequestContext.getCurrentContext();
        boolean hasLimitExceeded = Boolean.TRUE == context.getRequest().getAttribute("hasLimitExceeded");

        if (hasLimitExceeded) {
            ctx.setResponseBody(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }

        repository.save(new Request(
                context.getRequest().getRemoteAddr(),
                context.getRequest().getRequestURI(),
                context.getRequest().getMethod(),
                context.getResponse().getStatus(),
                context.getDuration(),
                new Date()
        ));
        logger.info("Saving request on cache, http status did {} ", context.getResponse().getStatus());

        return null;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
