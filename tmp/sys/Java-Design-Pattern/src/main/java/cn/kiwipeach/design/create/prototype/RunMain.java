/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.prototype;

import java.io.IOException;
import java.util.Date;

/**
 * Create Date: 2017/11/15 
 * Description: 测试入口:深复制与浅复制
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Deptment software = new Deptment("101","软件学院");
        Student zhansan = new Student("10086","张三",new Date());
        zhansan.setDeptment(software);
//        Student lisi = new Student("10087","李四",new Date());
        //1）zhansan2恰好与zhansan重名
        Student zhansan2 = (Student) zhansan.deepClone();
        //2）此时发现zhansan的生日也跟着变了，而事实，我们不希望zhansan2的生日修改影响到zhansan
        zhansan2.getBirthday().setTime(System.currentTimeMillis());
        zhansan2.getDeptment().setName("通讯学院");
        System.out.println(zhansan);
        System.out.println(zhansan2);
    }
}
