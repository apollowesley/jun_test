/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.proxy.cglib;

import cn.kiwipeach.design.structure.proxy.cglib.impl.TargetDaoImpl;
import cn.kiwipeach.design.structure.proxy.jdk.JDKDaoProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Create Date: 2017/11/19
 * Description: CGLIB动态代理工厂类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class CglibTargetDaoFactory {

    private Object targetObject;

    public CglibTargetDaoFactory(Object targetObject) {
        this.targetObject = targetObject;
    }
    /**
     * 给目标对象创建一个代理对象
     * @return
     */
    public TargetDaoImpl getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(targetObject.getClass());
        //3.设置回调函数
        en.setCallback(new TargetDaoMethodCallback());
        //4.创建子类(代理对象)
        return (TargetDaoImpl) en.create();
    }

    /**
     * CGLIB代理回调实现类
     */
    class TargetDaoMethodCallback implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            beforeMethod();
            Object invoke = null;
            try {
                invoke = method.invoke(CglibTargetDaoFactory.this.targetObject, objects);
                afterMethod();
            }catch (Exception e) {
                exceptionMethod(e);
            }
            returnMethod(invoke);
            return invoke;

        }
    }

    private void exceptionMethod(Exception e) {
        System.out.println("分析函数发现异常数据:"+e.getCause()+":"+e.getMessage());
    }

    private void afterMethod() {
        System.out.println("后置函数:执行分析函数后....");
    }

    private void beforeMethod() {
        System.out.println("前置函数:执行分析函数前....");
    }

    private void returnMethod(Object result){
        System.out.println("返回函数：执行分析函数返回:"+result);
    }

}
