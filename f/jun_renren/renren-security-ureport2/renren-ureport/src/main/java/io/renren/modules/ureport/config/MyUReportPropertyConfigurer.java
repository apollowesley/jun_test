package io.renren.modules.ureport.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;

/**
 * 继承UReportPropertyPlaceholderConfigurer, 装载application.yml
 * @author kool.zhao
 * 2020年5月24日
 */
public class MyUReportPropertyConfigurer extends UReportPropertyPlaceholderConfigurer {

	public MyUReportPropertyConfigurer(String path) {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource(path));
		this.setProperties(yaml.getObject());
	}
	
}
