package cn.jeeweb.tag.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web
 * @title: Web启动入口
 * @description: Web启动入口
 * @author: 王存见 https://blog.csdn.net/u012943767/article/details/79360748
 * @date: 2018/5/22 14:56
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,RedisAutoConfiguration.class})
public class TagTestBootApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TagTestBootApplication.class, args);
    }
}

