package com.evil.listener;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.evil.pojo.system.TypeDictionary;
import com.evil.service.TypeDictionaryService;

/**
 * spring 初始化完成后将日志写入session
 * 
 * @author frank_evil
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class InTypeListener implements ApplicationListener, ServletContextAware {

	// 注入ServletContext
	private ServletContext sc;
	@Resource
	private TypeDictionaryService dictionaryService;

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// 上下文刷新事件
		if (arg0 instanceof ContextRefreshedEvent) {
			// spring初始化完成时 将所有的类型写入servetContent
			if (sc != null) {
				List<TypeDictionary> paperTypes = dictionaryService.findDictionaryByType(TypeDictionary.PAPER_TYPE);
				sc.setAttribute("paperTypes", paperTypes);
				System.out.println("初始化所有的类型数据到Application中！");
			}else {
				System.out.println("初始化所有类型数据到Application中失败");
			}
		}

	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}

}
