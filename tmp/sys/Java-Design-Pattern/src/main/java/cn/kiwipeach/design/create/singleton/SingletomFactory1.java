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
 * Create Date: 2017/11/14 
 * Description: 第一种单列工厂
 * @author kiwipeach [1099501218@qq.com]
 */
public class SingletomFactory1{

    private static Apple apple;

    /**
     * 缺点：在多线程环境下，会出现问题
     * @return
     */
    public static Apple getInstance() throws InterruptedException {
        if(apple==null){
            Thread.sleep(3000); //这样就会搞出事情，解决方法见SingletomFactory2
            apple = new Apple();
        }
        return apple;
    }

    public static void main(String[] args) throws InterruptedException {
        //1)理想状态返回：true
//        Apple apple1 = SingletomFactory1.getInstance();
//        Apple apple2 = SingletomFactory1.getInstance();
//        System.out.println(apple1==apple2);
        //2)多线程返回：false
        SingletomFactory1 factory1 = new SingletomFactory1();
        MyThread1 task1 = new MyThread1();
        MyThread1 task2 = new MyThread1();
        new Thread(task1).start();
        new Thread(task2).start();

        //注意：一定要五秒后再去获取结果，等线程实例完对象再获取，否则null==null一直为true
        Thread.sleep(5000);
        System.out.println(task1.getApple());
        System.out.println(task2.getApple());
        System.out.println(task1.getApple()==task2.getApple());
        /*
        console:
        cn.kiwipeach.design.singleton.Apple@140e19d
        cn.kiwipeach.design.singleton.Apple@17327b6
        false
        */
    }

}

