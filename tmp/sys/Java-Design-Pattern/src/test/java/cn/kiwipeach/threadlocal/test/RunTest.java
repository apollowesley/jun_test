/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.threadlocal.test;

/**
 * Create Date: 2017/11/18 
 * Description: 测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunTest {

    public static void main(String[] args) throws InterruptedException {
        OdbcThread odbcThread1 = new OdbcThread();
        OdbcThread odbcThread2 = new OdbcThread();
        new Thread(odbcThread1,"Thead-1").start();
        new Thread(odbcThread2,"Thead-2").start();
        Thread.sleep(2000);
        System.out.println(odbcThread1.getConnection());
        System.out.println(odbcThread2.getConnection());

    }
}
