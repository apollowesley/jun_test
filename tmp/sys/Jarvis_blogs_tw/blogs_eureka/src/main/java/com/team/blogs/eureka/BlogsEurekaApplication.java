package com.team.blogs.eureka;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/7/10
 * @Version: 1.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaServer
public class BlogsEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogsEurekaApplication.class, args);

    }
}
