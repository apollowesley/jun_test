package org.buzheng.demo.web;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class DefaultWebApplicationInitializer implements
		WebApplicationInitializer {

	private final static String DISPATCHER_SERVLET_NAME = "dispatcher";
	private final static String DISPATCHER_SERVLET_MAPPING = "/";

	private final static String ENCODING = "utf-8";
	private final static String ENCODEING_FILTER_NAME = "encodefilter";
	private final static String ENCODEING_FILTER_MAPPING = "/*";

	public void onStartup(ServletContext servletContext)
			throws ServletException {

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);

		servletContext.addListener(new ContextLoaderListener(rootContext));

		// Spring web 控制器
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

		// 编码过滤器
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(ENCODING);
		encodingFilter.setForceEncoding(true);

		FilterRegistration.Dynamic encodingFilterRegistration = servletContext
				.addFilter(ENCODEING_FILTER_NAME, encodingFilter);
		encodingFilterRegistration.addMappingForUrlPatterns(null, true,
				ENCODEING_FILTER_MAPPING);

	}
}
