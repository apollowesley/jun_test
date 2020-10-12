/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.proxy.normal.proxy;

import cn.kiwipeach.design.structure.proxy.normal.TargetDao;
import cn.kiwipeach.design.structure.proxy.normal.impl.TargetDaoImpl;

/**
 * Create Date: 2017/11/19
 * Description: 接口静态代理
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class DaoProxy implements TargetDao {

    private TargetDao targetDao;

    public DaoProxy() {
        this.targetDao = new TargetDaoImpl();
    }

    @Override
    public void save(int param) {
        beforeMethod();
        try {
            targetDao.save(param);
            afterMethod();
        } catch (Exception e) {
            exceptionMethod(e);
        }
        returnMethod(999);
    }

    private void exceptionMethod(Exception e) {
        System.out.println("normal分析函数发现异常数据:"+e.getMessage());
    }

    private void afterMethod() {
        System.out.println("normal后置函数:执行分析函数后....");
    }

    private void beforeMethod() {
        System.out.println("normal前置函数:执行分析函数前....");
    }

    private void returnMethod(Object result){
        System.out.println("normal返回函数：执行分析函数返回:"+result);
    }
}
