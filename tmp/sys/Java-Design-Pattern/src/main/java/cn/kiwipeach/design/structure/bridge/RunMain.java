/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.bridge;

/**
 * Create Date: 2017/11/19 
 * Description: 桥接模式测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {

    public static void main(String[] args) {
        JDBCUtils jdbcUtils = new JDBCUtils();
        Driver mysqlDriver = new MysqlDriver();
        jdbcUtils.setDriver(mysqlDriver);
        jdbcUtils.exec();

        Driver oracleDriver = new OracleDriver();
        jdbcUtils.setDriver(oracleDriver);
        jdbcUtils.exec();

        /**
         console result is:
         MySQL数据库连接对象获取成功
         执行增删查改操作...
         Oracle数据库连接对象获取成功
         执行增删查改操作...
         */
    }
}
