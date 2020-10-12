/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

import java.lang.reflect.Method;

import org.jiucheng.web.annotation.RequestMapping;

public class Ctrl {
    
    private String route;
    private Method method;
    private RequestMapping requestMapping;
    private String beanName;
    private String handlerBeanName;
    
    public void setRoute(String route) {
        this.route = route;
    }
    
    public String getRoute() {
        return route;
    }

    public Method getMethod() {
        return method;
    }
    
    public RequestMapping getRequestMapping() {
        return requestMapping;
    }
    
    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }
    
    public void setMethod(Method method) {
        this.method = method;
    }
    
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    public String getBeanName() {
        return beanName;
    }

    public String getHandlerBeanName() {
        return handlerBeanName;
    }

    public void setHandlerBeanName(String handlerBeanName) {
        this.handlerBeanName = handlerBeanName;
    }
}
