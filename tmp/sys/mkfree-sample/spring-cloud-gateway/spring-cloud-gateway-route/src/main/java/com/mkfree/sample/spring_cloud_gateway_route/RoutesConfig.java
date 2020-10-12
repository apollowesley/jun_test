package com.mkfree.sample.spring_cloud_gateway_route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkfree.sample.spring_cloud_gateway_route.dto.Auth;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Configuration
public class RoutesConfig {
    private static final String API_HOST = "http://127.0.0.1:9011";


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 自定义路由方式
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 路由为/api/v1 前缀的转发到 127.0.0.1:9011

                // 指定当前时间后可以访问
                .route(r -> r.after(LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())).and()
                        .path("/api/v1/user/findOneRouteAfter").uri(API_HOST))

                // 指定当前时间前可以访问
                .route(r -> r.before(LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())).and()
                        .path("/api/v1/user/findOneRouteBefore").uri(API_HOST))
                // 指定 2018-12-30 前可以访问
                .route(r -> r.before(LocalDateTime.of(2018, 12, 30, 0, 0, 0)
                        .atZone(ZoneId.systemDefault())).and()
                        .path("/api/v1/user/findOneRouteBefore").uri(API_HOST))

                // 指定时间区间内可以访问 区间为：当前时间 - 2018-12-30
                .route(r -> r.between(LocalDateTime.now().atZone(ZoneId.systemDefault()),
                        LocalDateTime.of(2018, 12, 30, 0, 0, 0).atZone(ZoneId.systemDefault())).and()
                        .path("/api/v1/user/findOneRouteBetween").uri(API_HOST))

                // 指定 cookie，name:accessToken value:123456 可以访问
                .route(r -> r.cookie("accessToken", "123456").and()
                        .path("/api/v1/user/findOneRouteCookie").uri(API_HOST))

                // 指定 header，name = accessToken value = 123456
                .route(r -> r.header("accessToken", "123456").and()
                        .path("/api/v1/user/findOneRouteHeader").uri(API_HOST))

                // 指定 header，name = host value = 127.0.0.1，这里需要注意：其实host的用法其实就是请求头中的host对应的值
                .route(r -> r.host("**127.0.0.1**").and()
                        .path("/api/v1/user/findOneRouteHost").uri(API_HOST))

                // 指定 method 为 get 可以请求
                .route(r -> r.method(HttpMethod.GET).and()
                        .path("/api/v1/user/findOneRouteMethodGet").uri(API_HOST))
                // 指定 method 为 post 可以请求
                .route(r -> r.method(HttpMethod.POST).and()
                        .path("/api/v1/user/findOneRouteMethodPost").uri(API_HOST))

                // 指定 路径参数可以请求
                .route(r -> r.path("/api/v1/user/findOneRoutePath/{id}").uri(API_HOST))

                // 指定 查询参数可以请求
                .route(r -> r.query("accessToken", "123456").and()
                        .path("/api/v1/user/findOneRouteQuery").uri(API_HOST))

                // 指定 远程ip可以请求
                .route(r -> r.remoteAddr("127.0.0.1").and()
                        .path("/api/v1/user/findOneRouteRemoteAddr").uri(API_HOST))

                // 读取request body做指定条件访问
                .route(r -> r.readBody(String.class, body -> {
                    /*
                     * 注意这里有一个问题，readBody 里inClass目测只能是String.class，
                     * 直接转换对象时网关报java.lang.NullPointerException，不清楚是不是bug
                     */
                    Auth auth = null;
                    try {
                        auth = objectMapper.readValue(body, Auth.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 这里可以写很多逻辑代码
                    return auth != null && "123".equals(auth.getAccessToken());
                }).and().path("/api/v1/user/findOneRouteReadBody").uri(API_HOST))

                // 匹配路径、指定body、指定header、指定host、指定时间前 等等
                .route(r -> r.readBody(String.class, body -> {

                    /*
                     * 注意这里有一个问题，readBody 里inClass目测只能是String.class，
                     * 直接转换对象时网关报java.lang.NullPointerException，不清楚是不是bug
                     */
                    Auth auth = null;
                    try {
                        auth = objectMapper.readValue(body, Auth.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 这里可以写很多逻辑代码
                    return auth != null && "123".equals(auth.getAccessToken());
                }).and().host("**127.0.0.1**").and()
                        .before(LocalDateTime.of(2018, 12, 30, 0, 0, 0).atZone(ZoneId.systemDefault())).and()
                        .header("accessToken").and()
                        .path("/api/v1/user/findOneRouteCondition").uri(API_HOST))


                // 匹配路径、添加自定义规则（http method 为 get，请求头 accessToken 为 123456）、添加自定义请求头
                .route(r -> r.predicate(serverWebExchange -> {
                    // 添加指定规则

                    // 例如下面为 http method 为 get ， 请求头包含 accessToken value = 123456
                    HttpRequest httpRequest = serverWebExchange.getRequest();
                    HttpMethod httpMethod = httpRequest.getMethod();

                    boolean isMethod = httpMethod.matches(HttpMethod.GET.toString());

                    String accessToken = httpRequest.getHeaders().getFirst("accessToken");

                    return isMethod && "123456".equals(accessToken);
                }).and().path("/api/v1/user/findOneRouteCondition2")
                        // 添加请求头
                        .filters(gatewayFilterSpec -> {
                            gatewayFilterSpec.addRequestHeader("headerName1", "headerValue1");
                            gatewayFilterSpec.addRequestHeader("headerName2", "headerValue2");
                            return gatewayFilterSpec;
                        })
                        .uri(API_HOST))
                // 匹配前缀
//                .route(r -> r.path("/api/v1/**").uri(API_HOST))

                .build();
    }
}
