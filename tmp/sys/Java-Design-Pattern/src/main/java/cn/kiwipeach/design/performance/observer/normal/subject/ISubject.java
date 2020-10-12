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


import cn.kiwipeach.design.performance.observer.modal.News;
import cn.kiwipeach.design.performance.observer.normal.observer.IObserver;

import java.util.List;

/**
 * Create Date: 2017/11/21 
 * Description: 主题接口
 * @author kiwipeach [1099501218@qq.com]
 */
public interface ISubject {

    void addSubscriber(IObserver observer);

    void delSubscriber(IObserver observer);

    List<IObserver> getSubscriberList();

    News getCurSubjectNews();
}
