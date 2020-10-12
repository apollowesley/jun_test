package com.markbro.dzd.sso.core.filter;


import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.path.impl.AntPathMatcher;
import com.markbro.dzd.sso.core.user.SsoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明：
 * SsoWebFilterClient与SsoWebFilter
 * SsoWebFilterClient适合以jar包的形式引入sso,SsoWebFilter适合用于以源码的形式引入sso
 * 主要是因为变量赋值来源，SsoWebFilter需要spring读取配置文件注入。SsoWebFilterClient需要动态传入。
 */

public class SsoWebFilterClient extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SsoWebFilterClient.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();


    private String ssoServer;

    private String loginPath;

    private String logoutPath;

    private String excludedPaths;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();

        ssoServer = filterConfig.getInitParameter("ssoServer");
        loginPath = filterConfig.getInitParameter("loginPath");
        logoutPath = filterConfig.getInitParameter("logoutPath");
        excludedPaths = filterConfig.getInitParameter("excludedPaths");

            logger.info("SsoWebFilterClent init success.");

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // make url
        String servletPath = req.getServletPath();

        if(isStaticResources(servletPath)){//静态资源
            chain.doFilter(request, response);
            return;
        }
        // excluded path check
        if (excludedPaths!=null && excludedPaths.trim().length()>0) {
            for (String excludedPath:excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();

                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    return;
                }

            }
        }

        if (logoutPath!=null && logoutPath.trim().length()>0&& logoutPath.equals(servletPath)) {
            SsoHelper.logout(req,res);
            // redirect logout
            String logoutPageUrl = ssoServer.concat(SsoConf.SSO_LOGOUT_PATH);
            res.sendRedirect(logoutPageUrl);
            //chain.doFilter(request, response);
            return;
        }

        // valid login user, cookie + redirect
        SsoUser ssoUser = SsoHelper.loginChecked(req,res);

        // valid login fail
        if (ssoUser == null) {

            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {
                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println("{\"code\":"+SsoConf.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConf.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
                return;
            } else {
                // total link
                String link = req.getRequestURL().toString();
                // redirect logout
                String loginPageUrl = ssoServer.concat(loginPath)
                        + "?" + SsoConf.REDIRECT_URL + "=" + link;

                res.sendRedirect(loginPageUrl);
                return;
            }
        }
        // ser sso user
        request.setAttribute(SsoConf.SSO_USER, ssoUser);

        // already login, allow
        chain.doFilter(request, response);
        return;
    }
    //静态资源
    public static boolean isStaticResources(String uri){
        if(uri.endsWith(".static")||uri.endsWith(".ico")||uri.endsWith(".css")||uri.endsWith(".jpg")||uri.endsWith(".jpeg")||uri.endsWith(".png")||uri.endsWith(".bmp")||uri.endsWith(".gif")||uri.endsWith(".eot")||uri.endsWith(".svg")||uri.endsWith(".ttf")||uri.endsWith(".woff")){
            return true;
        }else{
            return false;
        }
    }
}
