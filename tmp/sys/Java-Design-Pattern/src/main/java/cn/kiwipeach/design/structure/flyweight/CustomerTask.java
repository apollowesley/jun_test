/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.flyweight;

import java.sql.Connection;

/**
 * Create Date: 2017/11/19 
 * Description: 数据源消费线程
 * @author kiwipeach [1099501218@qq.com]
 */
public class CustomerTask implements Runnable {

    private ConnectionPool connectionPool;
    private Connection connection;
    private int sleepTime;

    public CustomerTask(ConnectionPool connectionPool, Connection connection, int sleepTime) {
        this.connectionPool = connectionPool;
        this.connection = connection;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"获取到数据源..."+connection);
        try {
            System.out.println(Thread.currentThread().getName()+"正在使用数据源...（用10s）"+connection);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"使用过程中发生异常..."+connection);
            e.printStackTrace();
        }
        connectionPool.realease(connection);
        System.out.println(Thread.currentThread().getName()+"使用数据源完毕..."+connection);

    }
}
