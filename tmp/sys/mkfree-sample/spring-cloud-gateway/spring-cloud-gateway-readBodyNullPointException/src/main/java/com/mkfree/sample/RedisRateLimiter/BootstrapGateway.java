package com.mkfree.sample.RedisRateLimiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkfree.sample.RedisRateLimiter.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class BootstrapGateway {

    private static Logger log = LoggerFactory.getLogger(BootstrapGateway.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapGateway.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

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
                .route(r -> r.path("/api/v1/user/findOne").uri(API_HOST))


                // 正确写法
                .route(r -> r.readBody(String.class, body -> {
                    User user = null;
                    try {
                        user = objectMapper.readValue(body, User.class);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                    if (user == null) {
                        return false;
                    }
                    log.info("readBody2");
                    log.info("body user.id : {}", user.getId());
                    log.info("body user.name : {}", user.getName());
                    return true;
                }).and().path("^/api/v1/user/save2").uri(API_HOST))
                // 抛异常写法
                .route("readBody1", r -> r.readBody(User.class, user -> {
                    log.info("readBody1");
                    log.info("body user.id : {}", user.getId());
                    log.info("body user.name : {}", user.getName());
                    return true;
                }).and().path("/api/v1/user/save").uri(API_HOST))



                .build();
    }


}