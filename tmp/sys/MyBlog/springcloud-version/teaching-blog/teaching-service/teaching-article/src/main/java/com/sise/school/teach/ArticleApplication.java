package com.sise.school.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author idea
 * @data 2019/1/16
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableAsync
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class);
    }
}
