package com.alibaba.dubbo.test3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication  
@ImportResource("classpath:dubbo-demo-consumer-31883.xml")
public class ConsumerMain31883 implements EmbeddedServletContainerCustomizer{

	public static void main(String[] args) {
		SpringApplication.run(ConsumerMain31883.class,args);
		try {  
            System.in.read();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(28083);
	}

}
