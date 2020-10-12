/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.strategy.context;

import cn.kiwipeach.design.performance.strategy.strategy.Strategy;

/**
 * Create Date: 2017/11/19 
 * Description: 环境角色，持有一个Strategy的引用
 * @author kiwipeach [1099501218@qq.com]
 */
public class Context {

    private Strategy strategy;

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double calcPrice(double price){
        double result = strategy.makeDiscount(price);
        return  result;
    }
}
