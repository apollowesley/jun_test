package com.mkfree.sample.springmvcfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TwoFilter implements Filter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("two in");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("two out");
    }

    @Override
    public void destroy() {

    }
}
