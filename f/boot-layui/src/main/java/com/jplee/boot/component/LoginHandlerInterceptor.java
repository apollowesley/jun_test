package com.jplee.boot.component;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截器
 * @author jplee
 * @create 2019-01-25 17:55
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private List<String> url = new ArrayList();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userSession = (String) session.getAttribute("userSession"); //获取登录的session信息
        if(!StringUtils.isEmpty(userSession)){
            //已登录
            return true;
        }else{
//            response.sendRedirect("/login");	//未登录，跳转到登录页
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    /**
     * 定义排除拦截URL
     * @return
     */
    public List<String> getUrl(){
        url.add("/");
        url.add("/login");      //登录页
        url.add("/login.html");
        //网站静态资源 springboot已经做好了静态资源映射，可不配，但有时会不起作用
        url.add("/css/**");
        url.add("/js/**");
        url.add("/images/**");
        url.add("/layui/**");
        return url;
    }
}
