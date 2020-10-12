package com.gs.struts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by WangGenshen on 2/26/16.
 */
public class StrutsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        String uri = req.getRequestURI();
        System.out.println(servletPath);
        System.out.println(uri);

        String path = null;
        if (servletPath.equals("/product-input.action")) {
            path = "/WEB-INF/pages/input.jsp";
        } else if (servletPath.equals("/product-save.action")) {
            String name = req.getParameter("name");
            Product product = new Product(1, name);
            request.setAttribute("product", product);
            path = "/WEB-INF/pages/details.jsp";
        }

        if (path != null) {
            System.out.println(path);
            req.getRequestDispatcher(path).forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
