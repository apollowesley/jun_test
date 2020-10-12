package com.luoqy.speedy.config;

import org.springframework.boot.web.server.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class WebConfig
		extends WebMvcConfigurationSupport /* implements ErrorPageRegistrar */ {
	private static final String VIEW_PREFIX = "/WEB-INF/";// 视图前缀
	private static final String VIEW_SUFFIX = ".html";// 视图后缀
	private static final String VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";// 视图的内容类型。

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		// 允许跨域访问资源定义： /api/ 所有资源
		corsRegistry.addMapping("/api/**")
				// 只允许本地的9000端口访
				.allowedOrigins("http://localhost:8080", "http://localhost:8080", "http://127.0.0.1:8080",
						"http://127.0.0.1:80")
				.allowedOrigins("*")
				// 允许发送Cookie
				.allowCredentials(true)
				// 允许所有方法
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/system").setViewName("forward:/first/checkFirst");
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	/*
	 * @Override public void registerErrorPages(ErrorPageRegistry registry) {
	 * ErrorPage[] errorPages = new ErrorPage[2]; errorPages[0] = new
	 * ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"); errorPages[1] = new
	 * ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
	 * registry.addErrorPages(errorPages); }
	 */
	/*
	 * 过滤拦截器 (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.
	 * WebMvcConfigurationSupport#addInterceptors(org.springframework.web.
	 * servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/WEB-INF/**", "/common/**")
				.excludePathPatterns("/common/login/**", "/first/**", "/view/**");
		super.addInterceptors(registry);
	}

	/*
	 * 放行目录 (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.
	 * WebMvcConfigurationSupport#addResourceHandlers(org.springframework.web.
	 * servlet.config.annotation.ResourceHandlerRegistry)
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("D:/workspace/speedy/target/BOOT-INF/classes/properties/path.properties");
		// 2.通过file对象构建一个流对象
		FileInputStream fileInput = new FileInputStream(file);
		// 3.设置字节数组，1024只是一个习惯
		byte[] data = new byte[1024];
		StringBuffer stb = new StringBuffer();
		int leng;// 用来存储read(byte[] b)方法的返回值，代表每次读入的字节个数；当因为到达文件末尾而没有字节读入时，返回-1
		while ((leng = fileInput.read(data)) != -1) {
			stb.append(new String(data, 0, leng));
		}
		String string = new String(stb.toString().getBytes(), "GBK");
		String[] strs = string.split("\n|\r");
		for (String string2 : strs) {
			if (string2.contains("resourcesPath")) {
				System.err.println(string2.replace("resourcesPath=", ""));
			} else {

			}

		}

	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static", "classpath:/static/");
		try {
			Properties props = new Properties();
			InputStream in = null;
			in = WebConfig.class.getResourceAsStream("/properties/path.properties");
			props.load(in);
			String resourcesPath = props.getProperty("resourcesPath");
			String filePath = props.getProperty("filePath");
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources",
					"classpath:" + filePath + "BOOT-INF/classes");
			registry.addResourceHandler("/files/**").addResourceLocations("/files", "file:" + resourcesPath);
		} catch (Exception e) {
		}
		registry.addResourceHandler("/**").addResourceLocations("/", "classpath:/");

		super.addResourceHandlers(registry);
	}

	/**
	 * 配置 视图解析器
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setCache(false);
		resolver.setPrefix(VIEW_PREFIX);
		resolver.setSuffix(VIEW_SUFFIX);
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setContentType(VIEW_CONTENT_TYPE);
		return resolver;
	}

	/**
	 * 配置静态资源处理
	 * 
	 * @param configurer
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
