/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.iterator.collection;

import cn.kiwipeach.design.performance.iterator.iterator.IIterator;
import cn.kiwipeach.design.performance.iterator.iterator.MyStringIterator;

import java.util.List;

/**
 * Create Date: 2017/11/21 
 * Description: 我的自定义集合类
 * @author kiwipeach [1099501218@qq.com]
 */
public class MyStringCollection implements ICollection<String> {

    /**
     * 迭代对象
     */
    private List<String> listStr;

    public MyStringCollection(List<String> listStr) {
        this.listStr = listStr;
    }

    @Override
    public IIterator<String> iterator() {
        return new MyStringIterator(listStr);
    }
}
