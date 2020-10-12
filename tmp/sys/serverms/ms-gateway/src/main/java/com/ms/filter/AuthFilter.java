package com.ms.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuxiaomeng
 * @date 2019-03-28.
 * @email 154040976@qq.com
 */
@Component
public class AuthFilter extends ZuulFilter {
    /**
     * 拦截类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级 数字越大 优先级越低
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断拦截
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/auth/")){
                return false;
        }
        return true;
    }

    /**
     * 拦截执行方法
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //可以做其他校验
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.setResponseBody("{\"result\":\"token is empty!\"}");
            return null;
        }
        return null;
    }
}
