package io.renren.modules.ureport.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import com.bstek.ureport.console.UReportServlet;

/**
 * UReport 配置
 * @author kool.zhao
 * 2020年5月24日
 */
@Configuration
@ImportResource("classpath:ureport-console-context.xml")
public class UreportConfig implements EnvironmentAware {
	
	private Environment environment;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	@Bean
	public MyUReportPropertyConfigurer propertyrConfigurer(){
		String activeProfile = environment.getProperty("spring.profiles.active");
		if(StringUtils.isNotBlank(activeProfile)){
			activeProfile = "-" + activeProfile;
		}
		return new MyUReportPropertyConfigurer("application" + activeProfile + ".yml");
	}
	
    @Bean
    public ServletRegistrationBean<UReportServlet> buildUreportServlet(){
        return new ServletRegistrationBean<UReportServlet>(new UReportServlet(), "/ureport/*");
    }

}
