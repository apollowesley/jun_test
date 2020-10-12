package org.neuedu.fly.web;

import com.google.gson.Gson;
import org.neuedu.fly.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/18-12:57
 **/
public class BaseServlet extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected final String REDIRECT = "redirect:";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //乱码处理
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        //全局变量赋值
        request = req;
        response = resp;
        session = req.getSession();

        //客服端请求地址   student/add?aa
        String requestURI = req.getRequestURI();
        //获取请求方法名称 add
        //1. add方法开始位置，最后以一个 / 开始
        int startIndex = requestURI.lastIndexOf("/") + 1;
        //2. add方法结束位置，如果地址后面带有？这结束位置为？号的位置，否则为当前请求地址的长度
        int endIndex = requestURI.contains("?") ? requestURI.indexOf("?") : requestURI.length();
        //3. 通过截取方法获取请求地址中的请求方法名称
        String requestMethodName = requestURI.substring(startIndex, endIndex);
        System.out.println("请求地址：" + requestURI);
        System.out.println("请求方法：" + requestMethodName);
        try {
            //获取调用类中的所有公开方法
            Method invokeMethod = this.getClass().getMethod(requestMethodName);
            //如果查找的方法为空，则抛出异常
            if (Objects.isNull(invokeMethod)) {
                throw new RuntimeException("没有找到要执行的方法[" + requestMethodName + "]");
            }
            //如果查找的方法不为空，则执行该方法，并处理结果信息
            Object result = invokeMethod.invoke(this);

            //结果集处理: 返回JSON | 请求转发 | 请求重定向
            if (result instanceof String) {
                String url = (String) result;
                if (url.startsWith(REDIRECT)) {
                    url = url.substring(url.indexOf(":") + 1);
                    resp.sendRedirect(req.getContextPath() + "/" + url + ".jsp");
                    return;
                }
                req.getRequestDispatcher("/" + url + ".jsp").forward(req, resp);
                return;
            }
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(GsonUtil.toJson(result));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
