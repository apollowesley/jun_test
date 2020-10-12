/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.normal.proxy;

import cn.kiwipeach.design.performance.observer.normal.proxy.callback.SubjectProxyCallback;
import cn.kiwipeach.design.performance.observer.normal.subject.ISubject;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Create Date: 2017/11/21 
 * Description: Cglib的主题代理对象：目的是为了在主题对象中的属性发生变化时候，自动执行某些操作
 * @author kiwipeach [1099501218@qq.com]
 */
public class CglibSubjectProxy {
    /**
     * 代理主题对象
     */
    private ISubject iSubject;

    public CglibSubjectProxy(ISubject iSubject) {
        this.iSubject = iSubject;
    }

    public ISubject getInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(iSubject.getClass());
        //3.设置回调函数
        en.setCallback(new SubjectProxyCallback(iSubject));
        //4.创建子类(代理对象)
        return (ISubject) en.create();
    }


}
