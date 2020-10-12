/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.iterator.iterator;

/**
 * Create Date: 2017/11/21 
 * Description: 自定义迭代器接口
 * @author kiwipeach [1099501218@qq.com]
 */
public interface IIterator<T> {

    T previous();
    T next();
    boolean hasNext();
    T first();
    T last();

}
