package com.xieke.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootmybatisdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootmybatisdemoApplication.class, args);
    }

}
