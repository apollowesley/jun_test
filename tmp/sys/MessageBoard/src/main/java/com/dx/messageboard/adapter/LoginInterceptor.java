package com.dx.messageboard.adapter;

import com.dx.messageboard.common.Const;
import com.dx.messageboard.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  登录拦截器
 *  Create by zhoushiyu
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null) {
            return true;
        } else {
            //TODO 跳转页面
            //response.sendRedirect(request.getContextPath()+"/user/login");
            return true; //前端做拦截
        }
    }
}
