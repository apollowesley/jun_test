package com.jfast.web.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * zuul api 网关
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableZuulProxy
public class JfastWebZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfastWebZuulApplication.class, args);
    }

}
