package com.dtdream.rdic.saas.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by Ozz8 on 2015/07/02.
 */
public class SessUtils {
    public static void set (HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        if (null == session.getAttribute(key))
            session.setAttribute(key, value);
    }

    public static void reset (HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    public static Object get (HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    public static void cookie (HttpServletResponse response, String key, String value, Integer expire) {
        Integer age = 10 * 3600;
        if (null != expire && expire.compareTo(0) > 0)
            age = expire;
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    public static void clear (HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (null != session) {
            Enumeration<String> e = session.getAttributeNames();
            String attribute;
            while (e.hasMoreElements()) {
                attribute = e.nextElement();
                session.removeAttribute(attribute);
            }
            Cookie[] cookies = request.getCookies();
            Cookie cookie;
            if (null != cookies) {
                for (int i = 0; i < cookies.length; ++i) {
                    cookie = cookies[i];
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            session.invalidate();
        }
    }
}
