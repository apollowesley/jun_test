package com.mkfree.sample.RedisRateLimiterApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootstrapGateway {

    private static Logger log = LoggerFactory.getLogger(BootstrapGateway.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapGateway.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

    public static final String API_HOST = "http://127.0.0.1:9011";


    /**
     * 自定义路由方式
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/user/findOne").uri(API_HOST))

                // 添加请求头
                .route(r -> r.path("/api/v1/user/findOneRouteAddRequestHeader").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.addRequestHeader("headerName1", "headerValue1");
                    return gatewayFilterSpec;
                }).uri(API_HOST))

                // 添加请求参数
                .route(r -> r.path("/api/v1/user/findOneRouteAddRequestParameter").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.addRequestParameter("parameterName1", "parameterValue1");
                    return gatewayFilterSpec;
                }).uri(API_HOST))

                // 添加响应请求头
                .route(r -> r.path("/api/v1/user/findOneRouteAddResponseHeader").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.addResponseHeader("responseHeaderName1", "responseHeaderValue1");
                    return gatewayFilterSpec;
                }).uri(API_HOST))

                // 添加以上三者
                .route(r -> r.path("/api/v1/user/findOneRouteAddResponseHeader").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.addRequestHeader("requestHeaderName1", "requestHeaderValue1");
                    gatewayFilterSpec.addRequestParameter("parameterName1", "parameterValue1");
                    gatewayFilterSpec.addResponseHeader("responseHeaderName1", "responseHeaderValue1");
                    return gatewayFilterSpec;
                }).uri(API_HOST))

                // 添加前缀路劲
                // 下面例子相当于请求 http://127.0.0.1/api/api/v1/user/findOneRoutePrefixPath
                .route(r -> r.path("/api/v1/user/findOneRoutePrefixPath").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.prefixPath("/api");
                    return gatewayFilterSpec;
                }).uri(API_HOST))

                .route(r -> r.path("/api/v1/user/findOneRoutePreserveHostHeader").filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.preserveHostHeader();
                    return gatewayFilterSpec;
                }).uri(API_HOST))


                .build();
    }


}