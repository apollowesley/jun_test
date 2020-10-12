/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.iterator;

import cn.kiwipeach.design.performance.iterator.collection.ICollection;
import cn.kiwipeach.design.performance.iterator.collection.MyStringCollection;
import cn.kiwipeach.design.performance.iterator.iterator.IIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/11/22 
 * Description: 测试迭代器模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("apple");
        stringList.add("pinaple");
        stringList.add("banana");
        ICollection<String> iCollection = new MyStringCollection(stringList);

        IIterator<String> iterator = iCollection.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
