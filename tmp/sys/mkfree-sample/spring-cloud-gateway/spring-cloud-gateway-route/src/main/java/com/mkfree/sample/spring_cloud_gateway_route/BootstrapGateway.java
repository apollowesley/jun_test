package com.mkfree.sample.spring_cloud_gateway_route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
public class BootstrapGateway {

    private static Logger log = LoggerFactory.getLogger(BootstrapGateway.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapGateway.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

}