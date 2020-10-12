/**
 * Project Name:samples-patterns
 * Package Name:com.kind.samples.patterns.singleton
 * Created on:2016年9月21日下午5:25:57
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.samples.patterns.singleton.impl;

import com.kind.samples.patterns.singleton.Factory;

public class SingletonFactory implements Factory {
    private int count;

    private SingletonFactory() {
    }

    private static class SingletonHandler {
        private static SingletonFactory instance = new SingletonFactory();
    }

    public static SingletonFactory getInstance() {
        return SingletonHandler.instance;
    }

    @Override
    public int getCount() {
        return this.count++;
    }

}
