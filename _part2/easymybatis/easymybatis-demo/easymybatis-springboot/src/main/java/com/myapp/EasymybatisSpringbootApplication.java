package com.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com")
@SpringBootApplication
public class EasymybatisSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasymybatisSpringbootApplication.class, args);
    }
}
