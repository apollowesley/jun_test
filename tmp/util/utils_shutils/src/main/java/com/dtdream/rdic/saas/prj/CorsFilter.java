package com.dtdream.rdic.saas.prj;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * <!-- maven dependencies -->
 *
 */
@Component
public class CorsFilter implements Filter {
    public static String corsAccessControlAllowOrigin
        = "*";
    public static String corsAccessControlAllowMethods
        = "POST, GET, OPTIONS, DELETE";
    public static String corsAccessControlMaxAge
        = "3600";
    public static String corsAccessControlAllowHeaders
        = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With";
    public static String corsContentType
        = "application/json; charset=utf-8";
    public static String corsCharaterEncoding
        = "UTF-8";

    public static void config (HashMap<String,Object> configuration) {
        if (null == configuration || configuration.size() <= 0)
            return;
        if (configuration.containsKey("cors.AccessControlAllowOrigin"))
            corsAccessControlAllowOrigin = (String) configuration.get("cors.AccessControlAllowOrigin");
        if (configuration.containsKey("cors.AccessControlAllowMethods"))
            corsAccessControlAllowMethods = (String) configuration.get("cors.AccessControlAllowMethods");
        if (configuration.containsKey("cors.AccessControlMaxAge"))
            corsAccessControlMaxAge = (String) configuration.get("cors.AccessControlMaxAge");
        if (configuration.containsKey("cors.AccessControlAllowHeaders"))
            corsAccessControlAllowHeaders = (String) configuration.get("cors.AccessControlAllowHeaders");
        if (configuration.containsKey("cors.ContentType"))
            corsContentType = (String) configuration.get("cors.ContentType");
        if (configuration.containsKey("cors.CharaterEncoding"))
            corsCharaterEncoding = (String) configuration.get("cors.CharaterEncoding");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
        ) throws IOException, ServletException
    {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", corsAccessControlAllowOrigin);
        //可选，设置允许的跨域请求方法
        res.setHeader("Access-Control-Allow-Methods", corsAccessControlAllowMethods);
        //可选，设置跨域请求的老化时间
        res.setHeader("Access-Control-Max-Age", corsAccessControlMaxAge);
        res.addHeader("Access-Control-Allow-Headers", corsAccessControlAllowHeaders);
        res.setHeader("Content-Type", corsContentType);
        res.setContentType(corsContentType);
        res.setCharacterEncoding(corsCharaterEncoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
