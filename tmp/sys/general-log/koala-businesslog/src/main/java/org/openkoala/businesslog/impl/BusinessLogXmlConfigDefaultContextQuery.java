package org.openkoala.businesslog.impl;

import org.openkoala.businesslog.config.BusinessLogContextQuery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 11:35 AM
 */
public class BusinessLogXmlConfigDefaultContextQuery implements BusinessLogContextQuery {

    private String contextKey;

    private Object bean;

    private String method;

    private Object[] args;

    public BusinessLogXmlConfigDefaultContextQuery(String contextKey, Object bean, String method, Object[] args) {
        this.contextKey = contextKey;
        this.bean = bean;
        this.method = method;
        this.args = args;
    }

    @Override
    public Map<String, Object> query() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(contextKey, invoke(bean, method, args));
        return result;
    }

    private Object invoke(Object methodObject, String methodName, Object[] args) {
        Class ownerClass = methodObject.getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        try {
            Method method = ownerClass.getMethod(methodName, argsClass);
            return method.invoke(methodObject, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public String getContextKey() {
        return contextKey;
    }

    public void setContextKey(String contextKey) {
        this.contextKey = contextKey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }


}
