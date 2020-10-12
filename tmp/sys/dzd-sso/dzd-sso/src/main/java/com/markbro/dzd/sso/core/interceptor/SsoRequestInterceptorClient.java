package com.markbro.dzd.sso.core.interceptor;


import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.entity.SsoException;
import com.markbro.dzd.sso.core.entity.SsoNotLoginException;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.path.impl.AntPathMatcher;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 说明：
 * SsoRequestInterceptorClient适合用于以jar包的形式引入sso的场景
 *
 */
public class SsoRequestInterceptorClient extends HandlerInterceptorAdapter {




    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();


    Logger log= LoggerFactory.getLogger(SsoRequestInterceptorClient.class);


    @Value("${sso.ssoServer}")
    private String ssoServer;
    @Value("${sso.loginPath}")
    private String loginPath;
    @Value("${sso.logoutPath}")
    private String logoutPath;
    @Value("${sso.excludedPaths}")
    private String excludedPaths;//允许访问的url

    @Autowired
    ISsoLoginHelper ssoLoginHelper;


    private boolean isAjax(HttpServletRequest request, HandlerMethod handlerMethod){
        String header = request.getHeader("content-type");
        boolean isJson=  header!=null && header.contains("json");
        if(isJson||handlerMethod.getMethodAnnotation(ResponseBody.class) != null||((request.getHeader("accept").contains("application/json")  || (request.getHeader("X-Requested-With")!= null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest") )))){
            return true;
        }
        return false;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        if(!(handler instanceof HandlerMethod) ){
            //ssoLoginHelper.logout(request,response);
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        if(uri.equals(loginPath)||uri.equals(logoutPath)){
            return true;
        }
        if(isStaticResources(uri) || urlMatch(uri, excludedPaths)){
            return true;
        }

        try {
        SsoUser ssoUser = ssoLoginHelper.loginCheck(request, response);

        // valid login fail
        if (ssoUser == null) {

            String sessionId = CookieUtil.getValue(request, SsoConf.SSO_SESSIONID);
            if(ssoLoginHelper.isInvalidSession(sessionId)){
               String  msg = "您登录信息已经失效，请重新登录！";
               throw new SsoNotLoginException(msg);
            }

            if (isAjax(request,handlerMethod)) {
                // json msg
                String msg="{\"code\":"+ SsoConf.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConf.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}";
                responseString(response,msg,"application/json;charset=utf-8");
            } else {

                /*String link = request.getRequestURL().toString();

                String loginPageUrl = ssoServer.concat(loginPath)+ "?" + SsoConf.REDIRECT_URL + "=" + link;
                response.sendRedirect(loginPageUrl);
                return true;*/

                throw  new SsoNotLoginException("sso not login");
            }

        }
        // ser sso user
        request.setAttribute(SsoConf.SSO_USER, ssoUser);

        } catch (SsoException e) {
            log.error(e.getMessage());
            throw e;
        }
        return true;
    }

    protected String responseString(HttpServletResponse response, String msg,String contentType) {
        try {
            response.reset();
            response.setContentType(contentType!=null?contentType:"text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(msg);
            writer.close();
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    private boolean urlMatch(String servletPath,String excludedPaths){
        if (excludedPaths!=null && excludedPaths.trim().length()>0) {
            String[] paths=excludedPaths.split(",");
            for (String path:paths) {
                String uriPattern = path.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    return true;
                }
            }
        }
        return false;
    }
    //静态资源
    public static boolean isStaticResources(String uri){
        if(uri.indexOf("/resources/")>=0|| uri.indexOf("/front/")>=0||uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith(".jpg")||uri.endsWith(".jpeg")||uri.endsWith(".png")||uri.endsWith(".bmp")||uri.endsWith(".gif")||uri.endsWith(".eot")||uri.endsWith(".svg")||uri.endsWith(".ttf")||uri.endsWith(".woff")){
            return true;
        }else{
            return false;
        }
    }

}
