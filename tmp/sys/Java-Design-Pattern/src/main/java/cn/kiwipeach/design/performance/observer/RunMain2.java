/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer;

import cn.kiwipeach.design.performance.observer.jdk.observer.NewsObserver;
import cn.kiwipeach.design.performance.observer.jdk.subject.TechnologySubject;
import cn.kiwipeach.design.performance.observer.modal.News;

import java.util.Date;
import java.util.UUID;

/**
 * Create Date: 2017/11/21
 * Description: 测试JDK自带实现的观察者模式
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain2 {
    public static void main(String[] args) {

        TechnologySubject technologySubject = new TechnologySubject();

        NewsObserver newsObserver1 = new NewsObserver("小明");
        NewsObserver newsObserver2 = new NewsObserver("小红");

        technologySubject.addObserver(newsObserver1);
        technologySubject.addObserver(newsObserver2);

        News news = new News();
        news.setId(UUID.randomUUID().toString());
        news.setTitle("Your Mum Boom!");
        news.setContent("床前明月光，疑是地上霜");
        news.setSendDate(new Date());
        technologySubject.setTechnologyNews(news);
    }
}
