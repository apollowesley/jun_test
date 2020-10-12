package com.zhengjieblog.pocket.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.util.Jwt;
import com.zhengjieblog.pocket.util.ResponseData;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public class JwtInterceptor implements HandlerInterceptor {

    private String header = "Authorization";

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView model) throws Exception {
    }

    //拦截每个请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        final String requestHeader = request.getHeader(this.header);
        response.setCharacterEncoding("utf-8");
        ResponseData responseData = ResponseData.ok();
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);

            User user = Jwt.unsign(token, User.class);
            if(null != user) {
                return true;
            }
            else
            {
                responseData = ResponseData.forbidden();
                responseMessage(response, response.getWriter(), responseData);
                return false;
            }
        } else {
            responseData = ResponseData.forbidden();
            responseMessage(response, response.getWriter(), responseData);
            return false;
        }
    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseData responseData) {
        responseData = ResponseData.unauthorized();
        response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(responseData);
        System.out.print("不通过");
        out.print(json);
        out.flush();
        out.close();
    }
}
