package com.team.blogs.web;

import com.team.blogs.common.redis.interceptor.CacheKeyGenerator;
import com.team.blogs.common.redis.interceptor.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/7/10
 * @Version: 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /**
     * 在主函数内注入定义好的CacheKeyGenerator 接口
     * @return
     */
    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }
}
