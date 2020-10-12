package com.mkfree.sample.RedisRateLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@SpringBootApplication
public class BootstrapGateway {

    private static Logger log = LoggerFactory.getLogger(BootstrapGateway.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapGateway.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

    public static final String API_HOST = "http://127.0.0.1:9011";


    /**
     * 自定义限流标志的key，多个维度可以从这里入手
     * exchange对象中获取服务ID、用户信息、远程ip等
     */
    @Bean
    KeyResolver customKeyResolver() {
        return exchange -> {
            String hostname = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName();

            return Mono.just(hostname);
        };
    }
}