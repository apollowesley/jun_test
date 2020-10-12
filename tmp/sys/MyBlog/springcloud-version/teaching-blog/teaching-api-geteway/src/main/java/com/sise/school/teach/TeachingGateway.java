package com.sise.school.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author idea
 * @data 2019/1/16
 */
@SpringBootApplication
@EnableZuulProxy
public class TeachingGateway {

    public static void main(String[] args) {
        SpringApplication.run(TeachingGateway.class);
    }

}
