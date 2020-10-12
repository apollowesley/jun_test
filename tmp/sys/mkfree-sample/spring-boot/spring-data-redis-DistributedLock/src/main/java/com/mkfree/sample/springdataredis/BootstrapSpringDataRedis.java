package com.mkfree.sample.springdataredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootstrapSpringDataRedis {

    private static Logger log = LoggerFactory.getLogger(BootstrapSpringDataRedis.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapSpringDataRedis.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

}