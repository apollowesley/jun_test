package com.evil.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.evil.pojo.system.Right;
import com.evil.service.RightService;

/**
 *sping容器初始化完成后 ，初始化所有权限到ServletContentxt中 
 */
@Component
@SuppressWarnings("rawtypes")
public class IniRightListener implements ApplicationListener,ServletContextAware {

	//接受ServletContext
	private ServletContext sc;
	
	@Resource
	private RightService rightService;

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		
		//上下文刷新事件
		if(arg0 instanceof ContextRefreshedEvent){
			//查询所有的权限
			List<Right> rights=rightService.findAllEntities();
			Map<String, Right> rightMap=new HashMap<String, Right>();
			for (Right right : rights) {
				rightMap.put(right.getRightUrl(), right);
			}	
			if(sc!=null){
				sc.setAttribute("all_rights_map",rightMap);
				System.out.println("初始化所有权限到Application中!!!!");
			}
		}
	}

	//注入ServletContext
	@Override
	public void setServletContext(ServletContext arg0) {	
		this.sc=arg0;
	}

}
