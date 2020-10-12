/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.strategy.strategy;

import cn.kiwipeach.design.performance.strategy.strategy.Strategy;

/**
 * Create Date: 2017/11/19 
 * Description: 销售经理
 * @author kiwipeach [1099501218@qq.com]
 */
public class Manager implements Strategy {
    @Override
    public double makeDiscount(double price) {
        return 0.6*price;
    }
}
