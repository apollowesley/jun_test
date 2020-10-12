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

import java.util.List;

/**
 * Create Date: 2017/11/21 
 * Description: 我的迭代器
 * @author kiwipeach [1099501218@qq.com]
 */
public class MyStringIterator implements IIterator<String> {

    /**
     * 迭代对象
     */
    private List<String> listStr;

    /**
     * 当前索引
     */
    private int curIndex;

    public MyStringIterator(List<String> listStr) {
        this.listStr = listStr;
    }

    @Override
    public String previous() {
        if (curIndex>0){
            curIndex--;
        }
        return listStr.get(curIndex);
    }

    @Override
    public String next() {

        if(curIndex==listStr.size()-1){
            curIndex = listStr.size();
            return listStr.get(curIndex-1);
        }
        return listStr.get(curIndex++);
    }

    @Override
    public boolean hasNext() {
        if (curIndex<listStr.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String first() {
        return listStr.get(0);
    }

    @Override
    public String last() {
        return listStr.get(listStr.size()-1);
    }

}
