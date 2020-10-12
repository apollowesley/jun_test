/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.normal.observer;

import cn.kiwipeach.design.performance.observer.modal.News;

/**
 * Create Date: 2017/11/21 
 * Description: 观察者接口
 * @author kiwipeach [1099501218@qq.com]
 */
public interface IObserver {

    void update(News news);
}
