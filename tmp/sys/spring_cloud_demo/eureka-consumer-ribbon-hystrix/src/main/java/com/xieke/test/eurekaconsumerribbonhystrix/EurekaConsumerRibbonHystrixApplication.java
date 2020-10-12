package com.xieke.test.eurekaconsumerribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker // 启用断路器  @EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
//@SpringCloudApplication 包含上面引用的三个注解，Spring Cloud标准应用应包含服务发现以及断路器。
public class EurekaConsumerRibbonHystrixApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerRibbonHystrixApplication.class, args);
    }
}
