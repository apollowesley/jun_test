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
 * Description: 第三种单列工厂
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class SingletomFactory3 {
    private static Apple apple = new Apple();

    public static Apple getInstance() throws InterruptedException {
        return apple;
    }


    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        //1）类加载器加载SingletomFactory3，初始化Apple实例
        Class.forName("cn.kiwipeach.design.create.singleton.SingletomFactory3");
        MyThread3 task1 = new MyThread3();
        MyThread3 task2 = new MyThread3();
        new Thread(task1).start();
        new Thread(task2).start();
        //2）等待初始化完毕
        Thread.sleep(2000);
        System.out.println(task1.getApple());
        System.out.println(task2.getApple());
        System.out.println(task1.getApple() == task2.getApple());
    }
}
