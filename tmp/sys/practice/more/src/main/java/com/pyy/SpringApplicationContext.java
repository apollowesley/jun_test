/*
 * @(#) SpringApplicationContext.java 2015年6月4日
 * 
 * Copyright (c), 2009 深圳孔方兄金融信息服务有限公司（Shenzhen kfxiong
 * Financial Information Service Co. Ltd.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.pyy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Zhao Hui
 * @2015年6月4日
 */
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationCtx;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        
        applicationCtx = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        
        return applicationCtx;
    }
}
