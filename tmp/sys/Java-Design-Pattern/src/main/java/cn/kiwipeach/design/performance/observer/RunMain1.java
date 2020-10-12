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

import cn.kiwipeach.design.performance.observer.modal.News;
import cn.kiwipeach.design.performance.observer.normal.observer.IObserver;
import cn.kiwipeach.design.performance.observer.normal.observer.NewsObserver;
import cn.kiwipeach.design.performance.observer.normal.proxy.CglibSubjectProxy;
import cn.kiwipeach.design.performance.observer.normal.subject.ISubject;
import cn.kiwipeach.design.performance.observer.normal.subject.TechnologySubject;

import java.util.Date;
import java.util.UUID;

/**
 * Create Date: 2017/11/21 
 * Description: 测试观察者模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain1 {
    public static void main(String[] args) {
        //封装主题代理对象
        ISubject newsSubject = new TechnologySubject();
        TechnologySubject newsSubjectProxy = (TechnologySubject) new CglibSubjectProxy(newsSubject).getInstance();
        //观察者
        IObserver newsObserver1 = new NewsObserver("小明");
        IObserver newsObserver2 = new NewsObserver("小红");
        //订阅
        newsSubjectProxy.addSubscriber(newsObserver1);
        newsSubjectProxy.addSubscriber(newsObserver2);
        //发布新闻内容更新（set方法测试），看是否也会去通知？[答案:会]
        News news = new News();
        news.setId(UUID.randomUUID().toString());
        news.setTitle("Your Mum Boom!");
        news.setContent("床前明月光，疑是地上霜");
        news.setSendDate(new Date());
        newsSubjectProxy.setTechnologyNews(news);

        //主题其他方法测试（get方法测试），看是否也会去通知？[答案:不会]
        News curSubjectNews = newsSubjectProxy.getCurSubjectNews();
        System.out.println("新闻当前新闻内容为:"+curSubjectNews);
        /**
         console result is:
         小明:收到新闻更新内容...[News{id='f0d9c289-be6e-405a-ade0-84ede76779b3', title='Your Mum Boom!', content='床前明月光，疑是地上霜', sendDate=Tue Nov 21 16:30:40 CST 2017}]
         小红:收到新闻更新内容...[News{id='f0d9c289-be6e-405a-ade0-84ede76779b3', title='Your Mum Boom!', content='床前明月光，疑是地上霜', sendDate=Tue Nov 21 16:30:40 CST 2017}]
         新闻当前新闻内容为:News{id='f0d9c289-be6e-405a-ade0-84ede76779b3', title='Your Mum Boom!', content='床前明月光，疑是地上霜', sendDate=Tue Nov 21 16:30:40 CST 2017}
         */
    }
}
