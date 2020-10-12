package com.mkfree.sample.RedisRateLimiter;

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
                // 路由为/api/v2 前缀的转发到 127.0.0.1:9000
                .route(r -> r.path("/api/v2/**").uri("http://baidu.com"))
                .build();
    }


}