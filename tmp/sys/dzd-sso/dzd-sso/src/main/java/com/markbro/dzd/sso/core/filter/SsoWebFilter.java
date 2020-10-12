package com.markbro.dzd.sso.core.filter;


import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.path.impl.AntPathMatcher;
import com.markbro.dzd.sso.core.user.SsoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web sso filter
 *
 * ApplicationSsoFilterInitializer方式通过读取配置问价动态加载该过滤器，可以不用在web.xml配置该过滤器。能正常运行，但好像有些问题。 init方法没有执行！
 */

public class SsoWebFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SsoWebFilter.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${sso.ssoServer}")
    private String ssoServer;
    @Value("${sso.loginPath}")
    private String loginPath;
    @Value("${sso.logoutPath}")
    private String logoutPath;
    @Value("${sso.excludedPaths}")
    private String excludedPaths;

    @Autowired
    ISsoLoginHelper ssoLoginHelper;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();


        if(ssoLoginHelper==null){
            logger.error("SsoWebFilter init fail. ssoLoginHelper is null!");
        }else{
            logger.info("SsoWebFilter init success.");
        }

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
            chain.doFilter(request, response);
            return;
        }

        // valid login user, cookie + redirect
        SsoUser ssoUser = ssoLoginHelper.loginCheck(req, res);

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
