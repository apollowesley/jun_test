package com.site.authority.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.site.authority.service.xss.XSSHttpRequestWrapper;
import com.site.authority.service.xss.XSSSecurityConfig;
import com.site.authority.service.xss.XSSSecurityManager;

/**
 * 
 * xss攻击脚本过滤器
 *
 */
public class XSSSecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XSSSecurityFilter.class);

    /**
     * 销毁操作
     */
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("## XSSSecurityFilter destroy() begin ");
        }

        XSSSecurityManager.destroy();

        if (log.isDebugEnabled()) {
            log.debug("## XSSSecurityFilter destroy() end ");
        }
    }

    /**
     * 安全审核 读取配置信息
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否使用HTTP
        checkRequestResponse(request, response);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // http信息封装类
        XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(httpRequest);

        // 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
        if (xssRequest.validateParameter(httpResponse)) {
            if (XSSSecurityConfig.IS_LOG) {
                String paramStr = "";
                @SuppressWarnings("unchecked")
                Map<String, String[]> submitParams = httpRequest.getParameterMap();
                Set<String> submitNames = submitParams.keySet();
                for (String submitName : submitNames) {
                    String[] submitValues = submitParams.get(submitName);

                    for (String submitValue : (String[]) submitValues) {
                        paramStr = paramStr + submitValue;
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("##　XSS Security Filter RequestURL={} , param={} , RequestParameter={}", new Object[] { httpRequest.getRequestURL().toString(), paramStr, JSON.toJSONString(httpRequest.getParameterMap()) });
                }
            }
            // 是否中断操作
            if (XSSSecurityConfig.IS_CHAIN) {
                // request.setAttribute(MessageStyle.StyleKey, MessageStyle.ALERT_AND_BACK);
                // request.setAttribute(ExceptionWrapper.Message, LocaleHolder.getMessage("gap.authority.illegal_characters"));
                // RequestDispatcher rd = request.getRequestDispatcher(XSSSecurityConstants.FILTER_ERROR_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher("/error");
                rd.forward(request, response);
                return;
            }

        }
        chain.doFilter(request, response);
    }

    /**
     * 初始化操作
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        XSSSecurityManager.init(filterConfig);
    }

    /**
     * 判断Request ,Response 类型
     *
     * @param request
     *            ServletRequest
     * @param response
     *            ServletResponse
     * @throws javax.servlet.ServletException
     */
    private void checkRequestResponse(ServletRequest request, ServletResponse response) throws ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");

        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
    }
}
