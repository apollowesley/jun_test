/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.mediator;

/**
 * Create Date: 2017/11/25 
 * Description: 测试中介者模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        Student student1 = new Student("李白");
        Student student2 = new Student("杜甫");
        Medicator medicator = new Medicator(student1,student2);
        medicator.showInfo();
    }
}
