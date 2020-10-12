package org.openkoala.businesslog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于获取spring上下文的工具类
 * User: zjzhai
 * Date: 12/2/13
 * Time: 8:43 AM
 */
public class SpringApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new RuntimeException("spring\'s context is null");
        }
        return getApplicationContext().getBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
