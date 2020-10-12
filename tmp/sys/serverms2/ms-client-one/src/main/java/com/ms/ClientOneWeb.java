package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 客户端服务
 */
@SpringBootApplication
@EnableFeignClients("com.ms.client")
@EnableDiscoveryClient
@ComponentScan("com.ms")
@EnableAuthorizationServer
public class ClientOneWeb {
    public static void main(String[] args) {
        SpringApplication.run(ClientOneWeb.class, args);
    }

}
