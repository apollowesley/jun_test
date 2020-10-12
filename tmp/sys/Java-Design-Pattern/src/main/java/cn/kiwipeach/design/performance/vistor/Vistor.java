/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.vistor;

/**
 * Create Date: 2017/11/25 
 * Description: 宾客
 * @author kiwipeach [1099501218@qq.com]
 */
public class Vistor {
    private String name;

    public Vistor(String name) {
        this.name = name;
    }
    public void visit(Subject subject){
        System.out.println(this.name+"访问"+subject.getName());
        subject.showDrowWork();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
