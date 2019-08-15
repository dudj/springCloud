package com.linziyi.apigateway.filter;

import com.linziyi.apigateway.constant.RedisConstant;
import com.linziyi.apigateway.util.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限拦截-区分买家和卖家
 */
@Component
@Slf4j
public class AuthFilter extends ZuulFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("url:{}",request.getRequestURI());
        /**
         * /order/create  创建订单 - 只允许买家访问(cookie中有openid)
         * /order/finish 完成订单  - 只允许卖家访问(cookie中有token，并且对应的redis中有信息)
         * /product/list 产品列表 -  均可访问
         */
        if("/order/order/create".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, "openid");
            if(cookie == null || StringUtils.isEmpty(cookie.getValue())){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
        //这里需要验证redis中是否有数据
        if("/order/order/finish".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, "token");
            if(cookie == null
                    || StringUtils.isEmpty(cookie.getValue())
                    || StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return null;
    }
}
