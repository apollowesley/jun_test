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
 * Description: 第五种单列工厂(只是把第二种方法拆分了一下)
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class SingletomFactory5 {

    private static Object lock = new Object();

    private static Apple apple = null;


    public static void synInit(){
        synchronized (lock){
            if (apple==null){
                try{
                    apple = new Apple();
                }catch (Exception e){
                    //对初始化异常进行捕捉
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static Apple getInstance() {
        if (apple==null){
            synInit();
        }
        return apple;
    }


    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        //1）类加载器加载SingletomFactory3，初始化Apple实例
        Class.forName("cn.kiwipeach.design.create.singleton.SingletomFactory5");
        MyThread5 task1 = new MyThread5();
        MyThread5 task2 = new MyThread5();
        new Thread(task1).start();
        new Thread(task2).start();
        //2）等待初始化完毕
        Thread.sleep(2000);
        System.out.println(task1.getApple());
        System.out.println(task2.getApple());
        System.out.println(task1.getApple() == task2.getApple());
    }
}
