package com.mkfree.sample.filterPattern;

/**
 * 这个用于模仿处理，api跨域请求
 */
public class CorsFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        // 模仿允许所有来源
        request.getHeaders().put("Access-Control-Allow-Origin", "*");
        // 模仿允许所有请求方法 POST,GET,OPTIONS,DELETE 等等
        request.getHeaders().put("Access-Control-Allow-Methods", "*");

        System.out.println("正在添加跨域请求头");

        filterChain.doFilter(request, response);

        System.out.println("完成添加跨域请求头");
    }
}
