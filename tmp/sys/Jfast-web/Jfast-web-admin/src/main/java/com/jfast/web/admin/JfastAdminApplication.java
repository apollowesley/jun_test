package com.jfast.web.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jfast.web.admin", "com.jfast.api"})
public class JfastAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfastAdminApplication.class, args);
    }
}
