/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.adapter.iface;

/**
 * Create Date: 2017/11/15 
 * Description: Java方向的求职者
 * @author kiwipeach [1099501218@qq.com]
 */
public class JobSeeker extends ITManAdaptor {
    @Override
    public void canJava() {
        System.out.println("我是高级程序员。");
    }

    @Override
    public void canHtml() {
        System.out.println("我熟悉前端的JQuery、Ajax、BootStrap、LayUI等。");
    }

    @Override
    public void canLinux() {
        System.out.println("我从事过一年的运维工作，所以我对Linux很熟悉。");
    }

    @Override
    public void canOracle() {
        System.out.println("有两年的数据库使用经验。");
    }
}
