/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.templetemethod.way1;

/**
 * Create Date: 2017/11/19 
 * Description: 目标测试方法
 * @author kiwipeach [1099501218@qq.com]
 */
public class MyTargetMethod implements ITargetMethod {

    @Override
    public void invokeTarget(){
        System.out.println("目标方法调用开始.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("目标方法调用结束.");
    }
}
