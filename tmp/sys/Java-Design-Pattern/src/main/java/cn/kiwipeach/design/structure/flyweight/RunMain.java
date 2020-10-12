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
 * Description: 测试享元模式
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) throws InterruptedException {
        ConnectionPool connectionPool = new ConnectionPool(5);
        Connection connection1 = connectionPool.getConnection();
        Connection connection2 = connectionPool.getConnection();
        Connection connection3 = connectionPool.getConnection();
        Connection connection4 = connectionPool.getConnection();
        Connection connection5 = connectionPool.getConnection();
        CustomerTask connectionCustomer = new CustomerTask(connectionPool,connection1,10000);
        new Thread(connectionCustomer,"线程①").start();

        Connection connection6 = connectionPool.getConnection();
        System.out.println("connection6==>"+connection6);

        /**
         console result is:
         main线程发现数据源已经被用光了，尝试等待3s，在获取...
         线程①获取到数据源...oracle.jdbc.driver.T4CConnection@ee7fe8
         线程①正在使用数据源...（用10s）oracle.jdbc.driver.T4CConnection@ee7fe8
         main线程发现数据源已经被用光了，尝试等待3s，在获取...
         main线程发现数据源已经被用光了，尝试等待3s，在获取...
         main线程发现数据源已经被用光了，尝试等待3s，在获取...
         线程①使用数据源完毕...oracle.jdbc.driver.T4CConnection@ee7fe8
         connection6==>oracle.jdbc.driver.T4CConnection@ee7fe8
         */
    }
}
