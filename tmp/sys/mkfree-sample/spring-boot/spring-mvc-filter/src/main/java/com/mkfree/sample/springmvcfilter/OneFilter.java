package com.mkfree.sample.springmvcfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class OneFilter implements Filter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("one in");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("one out");
    }

    @Override
    public void destroy() {

    }
}
