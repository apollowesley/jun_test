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
 * Description: 测试基于类的适配器
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        //老的Java员工没有去按照经理的IJavaMan规范去学Linux所以不会
        //IJavaManAdapter oldJavaMan = new JavaMan();
        //oldJavaMan.canLinux();
        JavaMan oldJavaMan = new JavaMan();
        IJavaManAdapter newJavaMan = new NewJavaMan(oldJavaMan);
        newJavaMan.canLinux();
    }
}
