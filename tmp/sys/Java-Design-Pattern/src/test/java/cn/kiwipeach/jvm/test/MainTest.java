package cn.kiwipeach.jvm.test; /**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */

/**
 * Create Date: 2017/11/15 
 * Description: 测试主方法
 * @author kiwipeach [1099501218@qq.com]
 */
public class MainTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //儿子继承爸爸，爸爸有烟，儿子有书
//        Class.forName("cn.kiwipeach.jvm.test.Father");
        Class.forName("cn.kiwipeach.jvm.test.Son");
//        cn.kiwipeach.jvm.test.Father father = new cn.kiwipeach.jvm.test.Father();
//        cn.kiwipeach.jvm.test.Father father = new cn.kiwipeach.jvm.test.Son();
        /*
        * 1.父亲的构造
        * 2.父亲的烟
        * 3.父亲的静态代码块
        * 4.儿子的构造
        * 5.儿子的书
        * 6.儿子的静态代码块
        * */

        /**
         cn.kiwipeach.jvm.test.Smoke Constructor...
         cn.kiwipeach.jvm.test.Book Constructor...
         父亲的静态代码块...
         cn.kiwipeach.jvm.test.Father Constructor...
         儿子的静态代码块...
         cn.kiwipeach.jvm.test.Son Constructor...
         */
    }
}
