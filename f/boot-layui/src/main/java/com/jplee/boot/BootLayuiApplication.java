package com.jplee.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jplee.boot.mapper")
public class BootLayuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootLayuiApplication.class, args);
    }

}

