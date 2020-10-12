package com.mkfree.sample.filterPattern;

/**
 * 模仿 servlet
 */
public class Servlet {

    // 调用链
    private FilterChain filterChain = new FilterChain();

    public void service(Request request, Response response) {
        System.out.println("初始化过滤器");
        CorsFilter corsFilter = new CorsFilter();
        URLQueryToParameterFilter urlQueryToParameterFilter = new URLQueryToParameterFilter();
        System.out.println("添加过滤器");
        filterChain.addFilterChain(corsFilter);
        filterChain.addFilterChain(urlQueryToParameterFilter);
        System.out.println("前进请求过滤");
        filterChain.doFilter(request, response);
        System.out.println("往返请求过滤");

        System.out.println("完成过滤器后");

    }

}
