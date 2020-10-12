package org.simplestudio.restful.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.protocol.HttpClientContext;

public class SessionContext {

    //session信息的维护，key为用户名，value为httpclient的上下文信息。
    //对于同个用户，只要没有退出，都使用同个context，可以达到cookie的复用的效果
    private static Map<String, HttpClientContext> SESSION_MAP  = new HashMap<String, HttpClientContext>();

    public static String                          CURRENT_USER = null;

    public static HttpClientContext currentContext() {
        if (!SESSION_MAP.containsKey(CURRENT_USER)) {
            add(CURRENT_USER, new HttpClientContext());
        }

        return get(CURRENT_USER);
    }

    /**
     * 增加session信息
     * @param userName
     *      用户名，此处不做为空验证，确保有些不需要登录的请求也可以正常的设置进来
     * @param httpContext
     *      http请求的context，可以复用
     */
    public static void add(String userName, HttpClientContext httpContext) {
        if (!SESSION_MAP.containsKey(userName)) {
            SESSION_MAP.put(userName, httpContext);
        }
    }

    public static HttpClientContext get(String userName) {
        return SESSION_MAP.get(userName);
    }
}
