/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.adapter.obj;

/**
 * Create Date: 2017/11/15
 * Description: 新的JavaMan需要听从经理的去熟悉Linux技术(去实现IJavaMan规范)
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class NewJavaMan implements IJavaManAdapter {

    /**
     * 持有对象
     */
    private JavaMan javaMan;

    public NewJavaMan(JavaMan javaMan) {
        this.javaMan = javaMan;
    }

    @Override
    public void canOracle() {
        javaMan.canOracle();
    }

    @Override
    public void canJava() {
        javaMan.canJava();
    }

    @Override
    public void canHtml() {
        javaMan.canHtml();
    }

    @Override
    public void canLinux() {
        System.out.println("我已经把Linux学会了，哈哈。。。");
    }
}
