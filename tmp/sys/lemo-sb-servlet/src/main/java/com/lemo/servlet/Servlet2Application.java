package com.lemo.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/*
 * 
    * @className: Servlet1Application
    * @description: 扫描
    * @author liming
    * @date 2017年1月23日 下午2:02:29
    *
 */
@SpringBootApplication
@ServletComponentScan
public class Servlet2Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Servlet2Application.class, args);
	}
}
