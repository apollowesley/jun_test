package com.bodsite.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bodsite.common.config.properties.SystemProperty;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;

public class PropertyTest {
	
	private static Logger logger = LoggerFactory.getLogger(DemoConsumer.class);
	private ClassPathXmlApplicationContext context;

	public void setUp() {
		String[] files = { "classpath*:/spring/application-context.xml" };
		context = new ClassPathXmlApplicationContext(files);
	}
	
	public void handler(){
		 String group= SystemProperty.getProperty("dubbo.registry.group");
		 logger.info(group);
	}
	
    public void setDown() {
    	context.stop();
    	context.close();
    }

	public static void main(String[] args) {
		PropertyTest propertyTest = new PropertyTest();
		propertyTest.setUp();
		propertyTest.handler();
		propertyTest.setDown();
	}

}
