package com.kld.shop.interceptor;


import com.kld.sys.dto.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by an-han on 2015/10/4.
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        String path = request.getServletPath();
        /*if(path.matches(Constant.NO_INTERCEPTOR_PATH)){
            return true;
        }else{
            //shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            SysUser user = (SysUser)session.getAttribute(Constant.SESSION_USER);
            if(user!=null){
                return true;
            }else{
                //登陆过滤
                response.sendRedirect(request.getContextPath() + Constant.LOGIN);
                return false;
                //return true;
            }
        }*/
        return false;
    }

}
