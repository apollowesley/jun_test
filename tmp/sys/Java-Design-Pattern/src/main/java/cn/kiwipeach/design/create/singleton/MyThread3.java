/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.singleton;

/**
 * Create Date: 2017/11/15 
 * Description: 线程测试单例工厂类
 * @author kiwipeach [1099501218@qq.com]
 */
class MyThread3 implements Runnable{
    private Apple apple;


    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    @Override
    public void run() {
        try {
            apple = SingletomFactory3.getInstance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
