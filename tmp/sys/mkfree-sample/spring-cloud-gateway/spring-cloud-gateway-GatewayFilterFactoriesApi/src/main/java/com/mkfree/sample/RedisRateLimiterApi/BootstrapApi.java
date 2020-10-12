package com.mkfree.sample.RedisRateLimiterApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootstrapApi {

    private static Logger log = LoggerFactory.getLogger(BootstrapApi.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapApi.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

}