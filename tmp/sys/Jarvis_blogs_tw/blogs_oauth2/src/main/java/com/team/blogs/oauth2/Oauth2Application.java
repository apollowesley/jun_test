package com.team.blogs.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: xiaokai
 * @Description: 用户授权服务中心
 * @Date: 2019/7/11
 * @Version: 1.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Oauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }

}
