/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.normal.subject;

import cn.kiwipeach.design.performance.observer.normal.observer.IObserver;

import java.util.List;
import java.util.Vector;

/**
 * Create Date: 2017/11/21 
 * Description: 所有主题的抽象实现
 * @author kiwipeach [1099501218@qq.com]
 */
public abstract class AbstractSubject implements ISubject {

    private List<IObserver> subscriberList = new Vector();

    @Override
    public void addSubscriber(IObserver observer) {
        subscriberList.add(observer);
    }

    @Override
    public void delSubscriber(IObserver observer) {
        subscriberList.remove(observer);
    }

    @Override
    public List<IObserver> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(List<IObserver> subscriberList) {
        this.subscriberList = subscriberList;
    }
}
