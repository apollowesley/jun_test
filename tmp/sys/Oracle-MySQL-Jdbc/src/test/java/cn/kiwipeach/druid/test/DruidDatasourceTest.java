/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.druid.test;

import cn.kiwipeach.util.DBTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Create Date: 2017/12/06 
 * Description: 
 * @author kiwipeach [1099501218@qq.com]
 */
public class DruidDatasourceTest {

    /**
     * 测试数据源连接获取机制
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void testDruidDatasource() throws SQLException, InterruptedException {
        DBTools.initDataSource(null);
        DruidDataSource dataSource = DBTools.getDataSource();
        Connection [] connections = new Connection[20];
        for (int i=0;i<20;i++){
            connections[i] = dataSource.getConnection();
            System.out.println(connections[i]+"--"+i);
        }
        Connection connection21 = null;
        try {
            connection21 = dataSource.getConnection();
        } catch (GetConnectionTimeoutException e) {
            System.out.println("等了10s还没有等到数据源给连接资源,于是我抛出异常...");
            System.out.println("开始再次发起获取连接请求....");
            System.out.println("此时有资源已经被释放出来了connections[0]..."+connections[0]);
            connections[0].close();
            connection21 = dataSource.getConnection();
        }
        System.out.println("此时连接获取成功!"+connection21);
        System.out.println("程序结束");
    }
}
