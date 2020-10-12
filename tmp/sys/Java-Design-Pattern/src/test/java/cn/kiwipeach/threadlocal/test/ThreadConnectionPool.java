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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Create Date: 2017/11/18
 * Description: ThreadLocal类型的数据源连接池
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class ThreadConnectionPool {
    /**
     * 线程安全变量
     */
    private static ThreadLocal<Connection> thLocalConections = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott",
                        "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    public static Connection getConnection(){
        return thLocalConections.get();
    }

}
