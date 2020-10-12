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
 * Description: 第二种单列工厂
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class SingletomFactory2 {
    private static Object lock = new Object();
    private static Apple apple;

    /*1）同步整个方法*/
//    public static synchronized Apple getInstance() throws InterruptedException {
//        if(apple==null){
//            Thread.sleep(3000);
//            apple = new Apple();
//        }
//        return apple;
//    }
    /*2）同步代码块*/
    public static Apple getInstance() throws InterruptedException {
        if (apple == null) {
            System.out.println(Thread.currentThread().getName()+"第一次进来等待...");
            Thread.sleep(2000);
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName()+"实例化...");
                if(apple==null){
                    apple = new Apple();
                }
            }
        }
        System.out.println(Thread.currentThread().getName()+"实例化结果:"+apple);
        return apple;
    }


    public static void main(String[] args) throws InterruptedException {
        //2)多线程返回：false
        MyThread2 task1 = new MyThread2();
        MyThread2 task2 = new MyThread2();
        new Thread(task1).start();
        new Thread(task2).start();
        //注意：一定要五秒后再去获取结果，等线程实例完对象再获取，否则null==null一直为true
        Thread.sleep(5000);
        System.out.println(task1.getApple());
        System.out.println(task2.getApple());
        System.out.println(task1.getApple() == task2.getApple());
    }
}
