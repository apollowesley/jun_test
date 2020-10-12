/**
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.ioc.ClassTemplate;
import org.jiucheng.ioc.IOC;
import org.jiucheng.log.Logger;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.RequestMapping;
import org.jiucheng.web.handler.Handler;
import org.jiucheng.web.handler.HandlerMapping;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class ControllerIOC {
	
    protected static final Logger log = Logger.getLogger(ControllerIOC.class);
    
	private static boolean inited;
	private static List<String> listControllerBeanName = new ArrayList<String>();
	
	public static void init() {
		if(inited) return;
		@SuppressWarnings("unchecked")
		List<Class<?>> listClass = ClassTemplate.listClassByAnnotation(
				DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_SCANNER_PACKAGE),
				Controller.class);
		Controller controller;
		String beanName;
		for(Class<?> clazz : listClass) {
			controller = clazz.getAnnotation(Controller.class);
			if(controller != null) {
			    // init hanlder
			    Class<? extends Handler> handlerClass = controller.value();
			    builderHandler(handlerClass);
			    // \init handler
				beanName = IOC.createBeanName(null, clazz);
				try {
					IOC.put(beanName, clazz.newInstance());
					buildHandlerByControllerBeanName(beanName, handlerClass.getCanonicalName());
					listControllerBeanName.add(beanName);
					if(log.isInfoEnabled()) {
	                    log.info(beanName + " instanced of " + clazz.getName());
					}
				} catch (InstantiationException e) {
				    if(log.isErrorEnabled()) {
	                    log.error("", e);
				    }
				} catch (IllegalAccessException e) {
				    if(log.isErrorEnabled()) {
	                    log.error("", e);
				    }
				}
			}
		}
		injectControllerField();
		inited = true;
	}
	
	private static void builderHandler(Class<? extends Handler> clazz) {
	    String beanName = clazz.getCanonicalName();
	    Object rs = BeanFactory.get(beanName);
	    if(rs == null) {
	        try {
                IOC.put(beanName, clazz.newInstance());
                IOC.injectFieldByBeanName(beanName);
            } catch (InstantiationException e) {
                log.error("", e);
            } catch (IllegalAccessException e) {
                log.error("", e);
            }
	    }
	}
	
	private static void injectControllerField() {
		for(String controllerBeanName : listControllerBeanName) {
			IOC.injectFieldByBeanName(controllerBeanName);
		}
	}
	
    private static void buildHandlerByControllerBeanName(String beanName, String handlerBeanName) {
        if(StringUtil.isBlank(beanName)) return;
        Object controller = BeanFactory.get(beanName);
        List<Method> listMethod = ClassUtil.listMethod(controller.getClass());
        for(Method method : listMethod) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            if(rm != null) {
                rm(rm, handlerBeanName, beanName, method);
            }
        }
    }
    
    private static void rm(RequestMapping rm, String h, String b, Method m) {
        for(String v : rm.value()) {
            HandlerMapping.put(v, rm, h, b, m);
        }
    }
}
