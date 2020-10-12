/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.proxy.jdk;

import cn.kiwipeach.design.structure.proxy.jdk.impl.TargetDaoImpl;
import cn.kiwipeach.design.structure.proxy.normal.TargetDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create Date: 2017/11/19
 * Description: 接口静态代理
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class JDKDaoProxyFactory{

    private TargetDao targetDao;

    public JDKDaoProxyFactory() {
        this.targetDao = new TargetDaoImpl();
    }

    public TargetDao getTargetDaoProxy(){
        ClassLoader classLoader = targetDao.getClass().getClassLoader();
        Object targetProxy = Proxy.newProxyInstance(classLoader, targetDao.getClass().getInterfaces(), new TargetDaoInvokeHandle());
        return (TargetDao) targetProxy;
    }
    /**
     * 内部JDK代理回调
     */
    class TargetDaoInvokeHandle implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            beforeMethod();
            Object invoke = null;
            try {
                invoke = method.invoke(JDKDaoProxyFactory.this.targetDao, args);
                afterMethod();
            }catch (Exception e) {
               exceptionMethod(e);
            }
            returnMethod(invoke);
            return invoke;
        }
    }
    private void exceptionMethod(Exception e) {
        System.out.println("jdk分析函数发现异常数据:"+e.getCause()+":"+e.getMessage());
    }

    private void afterMethod() {
        System.out.println("jdk后置函数:执行分析函数后....");
    }

    private void beforeMethod() {
        System.out.println("jdk前置函数:执行分析函数前....");
    }

    private void returnMethod(Object result){
        System.out.println("jdk返回函数：执行分析函数返回:"+result);
    }
}


