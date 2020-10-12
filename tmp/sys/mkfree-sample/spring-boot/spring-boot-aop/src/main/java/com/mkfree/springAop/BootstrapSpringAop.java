package com.mkfree.springAop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootstrapSpringAop {

    private static Logger log = LoggerFactory.getLogger(BootstrapSpringAop.class);

    public static void main(String[] args) {
        SpringApplication.run(BootstrapSpringAop.class, args);
    }

}