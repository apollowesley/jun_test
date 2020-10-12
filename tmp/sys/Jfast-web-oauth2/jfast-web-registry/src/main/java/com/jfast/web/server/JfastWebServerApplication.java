package com.jfast.web.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JfastWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfastWebServerApplication.class, args);
    }

}
