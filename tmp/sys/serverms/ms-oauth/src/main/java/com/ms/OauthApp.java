package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * oauth 认证服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.ms")
@EnableFeignClients
@EnableAuthorizationServer
public class OauthApp {
    public static void main(String[] args) {
        SpringApplication.run(OauthApp.class, args);
    }
}
