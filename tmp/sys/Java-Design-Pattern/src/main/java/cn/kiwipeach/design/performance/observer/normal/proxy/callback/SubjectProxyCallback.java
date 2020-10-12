/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.normal.proxy.callback;

import cn.kiwipeach.design.performance.observer.normal.observer.IObserver;
import cn.kiwipeach.design.performance.observer.normal.subject.ISubject;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Create Date: 2017/11/21 
 * Description: 主题订阅代理回调函数
 * @author kiwipeach [1099501218@qq.com]
 */
public class SubjectProxyCallback implements MethodInterceptor {

    private ISubject iSubject;

    public SubjectProxyCallback(ISubject iSubject) {
        this.iSubject = iSubject;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invoke = null;
//        beforeMethod();
        try {
            invoke = method.invoke(iSubject, objects);
            if(method.getName().startsWith("set")||
                    method.getName().startsWith("delete")||
                    method.getName().startsWith("update")){
                //执行消息发布
                afterMethod();
            }
        }catch (Exception e) {
            exceptionMethod(e);
        }
        return invoke;
    }

    private void afterMethod() {
        List<IObserver> subscriberList = iSubject.getSubscriberList();
        for (IObserver observer:subscriberList){
            observer.update(iSubject.getCurSubjectNews());
        }
    }

    private void exceptionMethod(Exception e) {
        System.out.println("主题方法执行异常:"+iSubject);
    }
}
