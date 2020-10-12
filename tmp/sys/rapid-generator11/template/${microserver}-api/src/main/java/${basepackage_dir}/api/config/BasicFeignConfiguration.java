package ${basepackage}.api.config;

import feign.Contract;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

<#include "java_author.include">
public abstract class BasicFeignConfiguration {
	
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;
	
	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
	}

	@Bean
	public Encoder feignEncoder() {
		return new SpringEncoder(this.messageConverters);
	}
	
}
