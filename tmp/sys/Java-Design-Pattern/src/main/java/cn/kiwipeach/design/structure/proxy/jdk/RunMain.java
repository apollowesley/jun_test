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

import cn.kiwipeach.design.structure.proxy.normal.TargetDao;
import cn.kiwipeach.design.structure.proxy.normal.impl.TargetDaoImpl;

import java.lang.annotation.Target;

/**
 * Create Date: 2017/11/19 
 * Description: 测试JDK实现动态代理
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        TargetDao targetDao = new TargetDaoImpl();
        JDKDaoProxyFactory jdkDaoProxyFactory = new JDKDaoProxyFactory();
        TargetDao targetDaoProxy = jdkDaoProxyFactory.getTargetDaoProxy();
        System.out.println(targetDaoProxy.getClass());
        targetDaoProxy.save(10);
    }
}
