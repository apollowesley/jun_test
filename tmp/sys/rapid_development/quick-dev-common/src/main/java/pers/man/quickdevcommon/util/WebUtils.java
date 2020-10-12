package pers.man.quickdevcommon.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 网络工具类
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-01 20:26
 * @project quick-dev
 */
public class WebUtils {
    /**
     * 获取parameter
     *
     * @param name
     * @return
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取 HttpServletRequest 对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取 HttpServletResponse 对象
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取 HttpSession 对象
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取请求ip地址
     *
     * @param request
     * @return
     */
    public static String getClientAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 设置一个cookie
     *
     * @param key    cookie名称
     * @param value  cookie值
     * @param maxAge cookie失效时间
     * @param path   cookie的使用路径
     * @return
     */
    public static Cookie setCookie(String key, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        return cookie;
    }

    /**
     * 根据 key获取对应cookie
     *
     * @param key
     * @return
     */
    public static Cookie getCookie(String key) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 保存一条数据到session
     *
     * @param key
     * @param value
     */
    public static void setAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从session中取一条数据
     *
     * @param key
     * @return
     */
    public static Object getAttribute(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 获取ServletRequestAttributes对象
     *
     * @return
     */
    private static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    private WebUtils(){

    }
}
