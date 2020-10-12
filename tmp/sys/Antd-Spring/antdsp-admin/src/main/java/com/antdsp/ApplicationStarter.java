package com.antdsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.antdsp.common.AntdspRepositoryFactoryBean;
import com.antdsp.utils.ApplicationContextUtil;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = AntdspRepositoryFactoryBean.class)
public class ApplicationStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(ApplicationStarter.class);
		
		ConfigurableApplicationContext context = app.run(args);
		ApplicationContextUtil.setApplicationContext(context);
		
		logger.info("antdsp is started");
		
	}

}
