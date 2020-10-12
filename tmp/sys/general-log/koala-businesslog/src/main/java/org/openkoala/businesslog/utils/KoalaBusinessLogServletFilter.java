package org.openkoala.businesslog.utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: zjzhai
 * Date: 11/27/13
 * Time: 11:01 AM
 */
public abstract class KoalaBusinessLogServletFilter implements Filter {

    /**
     * 将需要用到的信息放入日志上下文
     *
     * @param req
     * @param resp
     * @param chain
     */
    public abstract void beforeFilter(ServletRequest req, ServletResponse resp, FilterChain chain);


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        beforeFilter(req, resp, chain);
        chain.doFilter(req, resp);
    }

    public String getIp(ServletRequest req){
        return req.getRemoteAddr();
    }


}
