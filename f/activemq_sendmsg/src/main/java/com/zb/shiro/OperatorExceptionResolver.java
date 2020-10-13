package com.zb.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Shiro全局身份验证错误视图转发
 * 
 * 作者: zhoubang 日期：2015年4月1日 下午5:32:42
 */
public class OperatorExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object obj, Exception exception) {
        ModelAndView mav = new ModelAndView();
        
        if(exception instanceof UnauthorizedException){//未授权，没有权限
            mav.setViewName("/unauthorized");
        }else if(exception instanceof ShiroException){//shiro系统错误
            mav.setViewName("/syserror");
        }else{//其他异常
            mav.setViewName("/user/500");
        }
        return mav;
    }

}
