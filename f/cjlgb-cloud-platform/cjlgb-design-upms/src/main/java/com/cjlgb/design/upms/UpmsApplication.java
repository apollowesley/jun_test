package com.cjlgb.design.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author WFT
 * @date 2020/7/13
 * description:Upms服务启动类
 */
@EnableFeignClients(value = "com.cjlgb.design.common.oauth.feign")
@SpringCloudApplication
@MapperScan(value = "com.cjlgb.design.upms.mapper")
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class,args);
    }

}
