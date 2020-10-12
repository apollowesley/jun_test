/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.jdk.observer;

import cn.kiwipeach.design.performance.observer.jdk.subject.TechnologySubject;
import cn.kiwipeach.design.performance.observer.modal.News;

import java.util.Observable;
import java.util.Observer;

/**
 * Create Date: 2017/11/21 
 * Description: 观察者
 * @author kiwipeach [1099501218@qq.com]
 */
public class NewsObserver implements Observer{
    /**
     * 订阅者昵称
     */
    private String name;

    public NewsObserver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TechnologySubject){
            TechnologySubject subject = (TechnologySubject) o;
            System.out.println(this.name+"收到:"+((TechnologySubject) o).getTechnologyNews()+" arg is "+arg);
        }
    }
}
