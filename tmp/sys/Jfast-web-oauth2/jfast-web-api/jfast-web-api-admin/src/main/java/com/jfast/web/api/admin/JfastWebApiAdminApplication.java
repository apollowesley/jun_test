package com.jfast.web.api.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.jfast.web")
@EnableEurekaClient
@MapperScan("com.jfast.web.api.admin.mapper")
public class JfastWebApiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfastWebApiAdminApplication.class, args);
    }

}
