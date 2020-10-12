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
 * Description: 东道主
 * @author kiwipeach [1099501218@qq.com]
 */
public class Subject {

    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public void accept(Vistor vistor){
        System.out.println("今天来访者:"+vistor.getName());
        vistor.visit(this);
    }

    /**
     * 展示画册
     */
    public void showDrowWork(){
        System.out.println(name+"展示东道主的画册");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
