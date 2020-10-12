package com.leo.vhr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.leo.vhr.mapper")
public class LearnApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(LearnApplication.class, args);
    }

}
