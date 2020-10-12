<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.api.config;

import ${basepackage}.api.properties.${cap_shortname}UsernamePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;

<#include "java_author.include">
@Configuration
public class ${cap_shortname}FeignConfiguration extends BasicFeignConfiguration {
	
	@Autowired
	private ${cap_shortname}UsernamePassword serviceUsernamePassword;

	/** 需要使用用户名密码登录才能访问接口 */
	@Bean
	public BasicAuthRequestInterceptor requestInterceptor() {
		return new BasicAuthRequestInterceptor(serviceUsernamePassword.get${cap_shortname}Username(), serviceUsernamePassword.get${cap_shortname}Password());
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}
