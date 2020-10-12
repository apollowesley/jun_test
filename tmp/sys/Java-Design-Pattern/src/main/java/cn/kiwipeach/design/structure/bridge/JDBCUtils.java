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
 * Description: 数据库连接池
 * @author kiwipeach [1099501218@qq.com]
 */
public class JDBCUtils {

    private Driver driver;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public void exec(){
        driver.getConnection();
        System.out.println("执行增删查改操作...");
    }
}
