package com.xieke.demo.springbootasyncdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootAsyncDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAsyncDemoApplication.class, args);
    }
}
