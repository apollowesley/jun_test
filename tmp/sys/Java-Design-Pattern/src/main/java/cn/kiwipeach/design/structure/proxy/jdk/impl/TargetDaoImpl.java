/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.proxy.jdk.impl;

import cn.kiwipeach.design.structure.proxy.normal.TargetDao;

/**
 * Create Date: 2017/11/19 
 * Description: 目标接口实现类
 * @author kiwipeach [1099501218@qq.com]
 */
public class TargetDaoImpl implements TargetDao {

    @Override
    public void save(int param) {
        System.out.println("正在执行数据分析........"+10/param);
    }
}
