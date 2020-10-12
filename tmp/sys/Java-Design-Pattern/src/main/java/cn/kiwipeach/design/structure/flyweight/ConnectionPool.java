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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/11/19
 * Description: 数据源连接池
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class ConnectionPool {
    /**
     * 获取数据源所对象和释放数据源锁对象
     */
    private Object getRock = new Object();
    private Object realseRock = new Object();

    /**
     * 数据库连接属性
     */
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private static final String USER_NAME = "scott";
    private static final String USER_PASSWORD = "123456";
    private static final String DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    private static final int DEFAULT_ACTIVE_SIZE = 10;

    /**
     * 空闲连接池
     */
    private List<Connection> idlePool = new ArrayList<Connection>();
    /**
     * 活跃状态连接池
     */
    private List<Connection> activePool = new ArrayList<Connection>();

    /**
     * 线程池大小：默认10个
     */
    private int maxActive;

    /**
     * 初始化数据源连接池
     */
    public ConnectionPool() {
        this(DEFAULT_ACTIVE_SIZE);
    }

    public ConnectionPool(int maxActive) {
        try {
            Class.forName(DRIVER_CLASS);
            for (int i = 0; i < maxActive; i++) {
                idlePool.add(DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据源连接
     *
     * @return
     */
    public  Connection getConnection() {
        if (idlePool.size() > 0) {
            Connection returnCon = null;
            synchronized (getRock){
                returnCon = idlePool.remove(0);
                activePool.add(returnCon);
            }
            return returnCon;
        } else {
            System.out.println(Thread.currentThread().getName()+"线程发现数据源已经被用光了，尝试等待3s，在获取...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getConnection();
        }
    }

    /**
     * 归还数据源
     */
    public void realease(Connection connection) {
        synchronized (realseRock){
            activePool.remove(connection);
            idlePool.add(connection);
        }
    }


    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public List<Connection> getIdlePool() {
        return idlePool;
    }

    public List<Connection> getActivePool() {
        return activePool;
    }
}
