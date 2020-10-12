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

/**
 * Create Date: 2017/11/19 
 * Description: cglib动态代理测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        TargetDaoImpl targetDaoImpl  = new TargetDaoImpl();
        CglibTargetDaoFactory cglibTargetDaoFactory = new CglibTargetDaoFactory(targetDaoImpl);
        TargetDaoImpl proxyInstance = cglibTargetDaoFactory.getProxyInstance();
        System.out.println(proxyInstance.getClass());
        proxyInstance.save(0);
        //
    }
}
