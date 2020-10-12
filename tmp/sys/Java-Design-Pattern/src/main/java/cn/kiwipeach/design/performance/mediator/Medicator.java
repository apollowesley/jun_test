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
 * Description: 终结者：持有关联对象的索引
 * @author kiwipeach [1099501218@qq.com]
 */
public class Medicator {
    private Student student1;
    private Student student2;

    public Medicator(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    public void showInfo(){
        System.out.println(student1.getName()+"的同桌是"+student2.getName());
    }
}
