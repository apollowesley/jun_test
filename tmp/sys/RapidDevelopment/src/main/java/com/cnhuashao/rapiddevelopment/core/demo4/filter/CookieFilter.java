package com.cnhuashao.rapiddevelopment.core.demo4.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 类 {@code CookieFilter} 会话Cookie拦截 <br> 用于对所有web会话的Cookie进行安全检查.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/5 20:10
 */
@Slf4j
public class CookieFilter implements Filter{
    //private static final Logger log = LoggerFactory.getLogger(CookieFilter.class);

    /**
     * 继承方法，拦截器初始化逻辑
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 自定义拦截器，用于Cookie全局设置
     * @param request 请求
     * @param response 响应
     * @param chain  filterchain是servlet容器提供给开发人员的对象
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //日志打印
        if(log.isDebugEnabled()){
            String url = req.getServletPath();
            log.debug("-------- get into Cookie filter {} {}",new Date(),url);
        }
        //获取请求中的cookies
        Cookie[] cookies = req.getCookies();
        //如果不为空，则开始对其中的所有cookie进行设置
        if (cookies!=null){
            for (Cookie cookie : cookies){
                if (cookie!=null){
                    //设置cookie最大有效期，单位秒，当前设置一小时60*60
                    cookie.setMaxAge(3600);
                    //向浏览器指定，只允许https协议下才可以发送cookie
                    cookie.setSecure(true);
                    //设置cookie只能使用
                    cookie.setHttpOnly(true);
                    resp.addCookie(cookie);
                }
            }
        }
        //请求下发
        chain.doFilter(req,resp);
    }

    /**
     * 继承方法，在销毁filter时进行的操作
     */
    @Override
    public void destroy() {

    }
}
