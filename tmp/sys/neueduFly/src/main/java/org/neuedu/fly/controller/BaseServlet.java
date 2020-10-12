package org.neuedu.fly.controller;

import org.neuedu.fly.entity.vo.ResponseEntity;
import org.neuedu.fly.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description
 * @auther: Lily
 * @date: 2019/10/17-10:55
 **/
public class BaseServlet extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected final String REDIRECT = "redirect:";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        request = req;
        response = resp;
        session = req.getSession();
        String uri = req.getRequestURI();
        //通过请求地址获取到对应的方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        //使用反射获取指定方法名称的方法对象  Class
        Class<? extends BaseServlet> clazz = this.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //打破规则，调用私有方法
        method.setAccessible(true);
        //执行方法，获取结果
        Object result = null;
        try {
            result = method.invoke(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(result)){
            if(result instanceof ResponseEntity){
                //JSON转换
                String jsonStr = GsonUtil.toJson(result);
                //设置返回格式
                resp.setContentType("application/json;charset=utf-8");
                //out输出
                PrintWriter out = resp.getWriter();
                out.write(jsonStr);
                out.close();
            }else if(result instanceof String){
                String path = result + ".jsp";
                //重定向
                if(path.startsWith(REDIRECT)){
                    path = path.substring(path.indexOf("/"));
                    resp.sendRedirect(path);
                    return;
                }
                req.getRequestDispatcher(path).forward(req,resp);
            }else{
                throw new RuntimeException("数据格式不匹配，想清楚。。");
            }
        }


    }
}
