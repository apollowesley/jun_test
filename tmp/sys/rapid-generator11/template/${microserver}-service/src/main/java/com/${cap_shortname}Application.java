package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${basepackage}.config.WebConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

<#include "java_author.include">
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories("com.**")
public class ${cap_shortname}Application {

    private static Logger logger = LoggerFactory.getLogger(${cap_shortname}Application.class);

    /**
     * Start
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(${cap_shortname}Application.class, WebConfig.class).run(args);
        logger.info("${cap_shortname}Application SpringBoot Start Success");
        logger.info("http://127.0.0.1:${server_port}/swagger-ui.html");
    }

}
