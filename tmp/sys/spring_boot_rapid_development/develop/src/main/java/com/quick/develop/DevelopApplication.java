package com.quick.develop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quick.develop.mapper")
public class DevelopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevelopApplication.class, args);
	}

}
