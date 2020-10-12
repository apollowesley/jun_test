package com.springapp.mvc;

import org.openkoala.businesslog.utils.ThreadLocalBusinessLogContext;
import org.openkoala.businesslog.utils.KoalaBusinessLogServletFilter;

import javax.servlet.*;

/**
 * User: zjzhai
 * Date: 11/27/13
 * Time: 11:01 AM
 */
public class LogFilter extends KoalaBusinessLogServletFilter {


    /**
     * 将需要用到的信息放入日志上下文
     *
     * @param req
     * @param resp
     * @param chain
     */
    @Override
    public void beforeFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        ThreadLocalBusinessLogContext.put("user", req.getParameterMap());
        ThreadLocalBusinessLogContext.put("ip", getIp(req));
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
