package com.cdh.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cdh.springboot.mapper")
public class PermissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionApplication.class, args);
	}
}
