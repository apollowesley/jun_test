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

import java.sql.Connection;

/**
 * Create Date: 2017/11/18 
 * Description: 测试获取数据库连接线程
 * @author kiwipeach [1099501218@qq.com]
 */
public class OdbcThread  implements Runnable{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("CurentThread is "+Thread.currentThread().getName());
        connection = ThreadConnectionPool.getConnection();
    }
}
