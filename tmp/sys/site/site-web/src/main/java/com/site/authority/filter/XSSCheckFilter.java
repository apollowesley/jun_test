package com.site.authority.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.site.common.constants.Constants;

/**
 * 
 * xss参数防注入
 * 
 */
public class XSSCheckFilter implements Filter {

    @SuppressWarnings("unused")
    private FilterConfig config;

    // 出错跳转的目的地
    private static String errorPath;
    // 不进行拦截的url
    private static String[] excludePaths;
    // 需要拦截的JS字符关键字
    private static String[] safeless;

    @SuppressWarnings("rawtypes")
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        Enumeration params = req.getParameterNames();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // String basePath = request.getScheme() + "://" +
        // request.getServerName() + ":" + request.getServerPort() + "/";
        boolean isSafe = true;
        String requestUrl = request.getRequestURI();
        // String queryUrl = request.getQueryString();
        if (isSafe(requestUrl)) {
            requestUrl = requestUrl.substring(requestUrl.indexOf("/"));
            if (!excludeUrl(requestUrl)) {
                while (params.hasMoreElements()) {
                    String cache = req.getParameter((String) params.nextElement());
                    if (!"".equals(cache) && null != cache) {
                        if (!isSafe(cache)) {
                            isSafe = false;
                            break;
                        }
                    }
                }
            }
        } else {
            isSafe = false;
        }
        if (!isSafe) {
            request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
            request.getRequestDispatcher(errorPath).forward(request, response);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    private static boolean isSafe(String str) {
        if (!"".equals(str) && null != str) {
            for (String s : safeless) {
                if (str.toLowerCase().contains(s)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean excludeUrl(String url) {
        if (excludePaths != null && excludePaths.length > 0) {
            for (String path : excludePaths) {
                if (url.toLowerCase().equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        try {
            errorPath = Constants.XSS_ERROR_PATH;
            String excludePath = Constants.XSS_EXCLUDE_PATHS;
            if (!"".equals(excludePath) && null != excludePath) {
                excludePaths = excludePath.split(",");
            }
            String safeles = Constants.XSS_SAFELESS;
            if (!"".equals(safeles) && null != safeles) {
                safeless = safeles.split(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
