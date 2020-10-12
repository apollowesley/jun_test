package com.handy.interceptor;

import com.handy.common.constants.Constants;
import io.swagger.models.HttpMethod;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author handy
 * @Description: {用户登录拦截器}
 * @date 2019/9/2 17:57
 */
@Component
public class MemberLoginInterceptor implements HandlerInterceptor {
    private static final String API_PATH_PREFIX = "/api";

    /**
     * 在业务处理器处理请求之前被调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据 URL 规则做不容类型请求的处理
        String uri = request.getRequestURI();
        //根据用户session校验权限
        boolean flag = false;
        val userVo = request.getSession().getAttribute(Constants.USERSESSION);
        if (userVo == null) {
            if (uri.startsWith(API_PATH_PREFIX) && HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
                flag = true;
            } else {
                response.sendRedirect("/");
            }
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 在业务处理器处理请求完成之后，生成视图之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
