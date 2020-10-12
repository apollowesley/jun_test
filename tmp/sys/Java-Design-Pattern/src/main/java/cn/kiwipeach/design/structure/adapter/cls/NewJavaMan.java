/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.adapter.cls;

/**
 * Create Date: 2017/11/15 
 * Description: 新的JavaMan需要听从经理的去熟悉Linux技术(去实现IJavaMan规范)
 * @author kiwipeach [1099501218@qq.com]
 */
public class NewJavaMan extends JavaMan implements IJavaManAdapter {
    /**
     * 继承了父类的属性方法
     */

    @Override
    public void canLinux() {
        System.out.println("我已经把Linux学会了，哈哈。。。");
    }
}
