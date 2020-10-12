package interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("admin");
        if (user != null) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String name = handlerMethod.getMethod().getName();
        if (name.equals("loginCheck") || name.equals("login") || name.equals("index") || name.equals("main") || name.equals("aboutUs")) {
            return true;
        }
        request.getRequestDispatcher("login.action").forward(request, response);//跳转到登录页面
        return false;//拦截
    }
}
