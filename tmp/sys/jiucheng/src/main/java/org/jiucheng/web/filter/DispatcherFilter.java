/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.ioc.IOC;
import org.jiucheng.log.Logger;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.web.ControllerIOC;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.handler.Handler;
import org.jiucheng.web.handler.HandlerMapping;
import org.jiucheng.web.util.WebUtil;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class DispatcherFilter implements Filter {

    protected static final Logger log = Logger.getLogger(DispatcherFilter.class);
    
    public void destroy() {
        log.info("stopped");
    }

    public void doFilter(ServletRequest sreq,
            ServletResponse sres, FilterChain chain)
            throws IOException, ServletException {
    	if(!(sreq instanceof HttpServletRequest) || !(sres instanceof HttpServletResponse)) {
    	    log.error("Illegal Access");
    		throw new ServletException("Illegal Access");
    	}
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse res = (HttpServletResponse) sres;
        WebUtil.setRequest(req);
        WebUtil.setResponse(res);
        WebWrapper webWrapper = HandlerMapping.getWebWrapper();
        if(null != webWrapper) {
            Handler handler = BeanFactory.get(webWrapper.getCtrl().getHandlerBeanName(), Handler.class);
            try {
                if(handler.before(webWrapper)) {
                    handler.invoke(webWrapper);
                }
            }finally {
                handler.after(webWrapper);
            }
        }else {
            chain.doFilter(sreq, sres);
        }
    }

    public void init(FilterConfig fc) throws ServletException {
        String cfg = fc.getInitParameter("cfg");
        if(null == cfg || "".equals(cfg.trim())) {
            cfg = DefaultPropertiesConstant.JIUCHENG_PROPERITES;
        }
        cfg = cfg.trim();
        if(!DefaultPropertiesUtil.getIsLoaded()) {
            if(cfg.toLowerCase().endsWith(".properties")) {
                DefaultPropertiesUtil.load(cfg);
            }else if(cfg.toLowerCase().endsWith(".xml")) {
                DefaultPropertiesUtil.loadFromXml(cfg);
            }
        }
    	IOC.init();
    	ControllerIOC.init();
    	if(log.isInfoEnabled()) {
            log.info("inited");
    	}
    }
}
