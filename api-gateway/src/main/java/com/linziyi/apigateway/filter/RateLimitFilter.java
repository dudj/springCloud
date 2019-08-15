package com.linziyi.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.linziyi.apigateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流拦截器
 */
public class RateLimitFilter extends ZuulFilter {
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if(!RATE_LIMITER.tryAcquire()){
            throw new RateLimitException();
        }
        return null;
    }
}
