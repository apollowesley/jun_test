package com.mkfree.sample.filterPattern;

import java.util.stream.Stream;

/**
 * 这个过滤去主要把 url?后的参数转换成parameter
 *
 * 例如：http://blog.mkfree.com/post?id=1
 */
public class URLQueryToParameterFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {

        String url = request.getUrl();
        int index = url.indexOf("?");
        if (index != -1) {
            String parameterURL = url.substring(index);

            String[] parameters = parameterURL.split("&");

            for (String parameter : parameters) {
                String paramName = parameter.split("=")[0];
                String paramValue = parameter.split("=")[1];
                request.getParams().put(paramName, paramValue);
            }
        }
        System.out.println("正在把URL查询参数转换Parameter参数");
        filterChain.doFilter(request, response);
        System.out.println("完成把URL查询参数转换Parameter参数");
    }
}
