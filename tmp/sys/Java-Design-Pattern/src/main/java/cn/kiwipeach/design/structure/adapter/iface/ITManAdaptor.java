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
 * Description: 此时，有一位IT男需要找Java工作，他需要在自己的简历上构思他会的技术;
 * 会的技术就设置为抽象的,不会的技术这里筛出不实现
 * @author kiwipeach [1099501218@qq.com]
 */
public abstract class ITManAdaptor implements ITMan{

    @Override
    public abstract void canJava();

    @Override
    public void canCSharp() {

    }

    @Override
    public void canCplus() {

    }

    @Override
    public abstract void canHtml();

    @Override
    public abstract void canLinux() ;

    @Override
    public abstract void canOracle();

    @Override
    public void canManager() {

    }
}
