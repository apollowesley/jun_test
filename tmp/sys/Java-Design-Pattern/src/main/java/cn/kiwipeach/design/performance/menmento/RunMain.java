/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.menmento;

import java.lang.reflect.InvocationTargetException;

/**
 * Create Date: 2017/11/23 
 * Description: 备忘录模式测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) throws CloneNotSupportedException, InvocationTargetException, IllegalAccessException {
        Student student1 = new Student("10086","刘卜铷","123456","kiwipeach@qq.com");
        System.out.println("保存前1:"+student1);
        Storeage.save2Store(student1);
        student1.setPassword("lbr50789");
        System.out.println("修改后1:"+student1);
        Object student2 = Storeage.restoreObj(student1);
        System.out.println("恢复后2:"+student2);
        System.out.println("备忘和恢复前后是否为同一个对象?"+(student1==student2));
        /**
         console result is:
         保存前:Student{id='10086', name='刘卜铷', password='123456', email='kiwipeach@qq.com'}
         修改后:Student{id='10086', name='刘卜铷', password='lbr50789', email='kiwipeach@qq.com'}
         恢复后:Student{id='10086', name='刘卜铷', password='123456', email='kiwipeach@qq.com'}
         true
         */

    }
}
