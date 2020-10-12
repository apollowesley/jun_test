package com.xinhuo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author 张新林
 * 时间 2019/1/20 17:36
 * 描述3
 */
@SpringBootApplication
@MapperScan("com.xinhuo.demo.dao")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
