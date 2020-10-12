package com.jfast.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "com.jfast.web.auth", "com.jfast.web.common"
})
@EnableEurekaClient
@EnableHystrixDashboard
@EnableFeignClients
public class JfastWebAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfastWebAuthApplication.class, args);
    }

}
