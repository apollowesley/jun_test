/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.strategy;

import cn.kiwipeach.design.performance.strategy.context.Context;
import cn.kiwipeach.design.performance.strategy.strategy.Manager;
import cn.kiwipeach.design.performance.strategy.strategy.SaleMan;
import cn.kiwipeach.design.performance.strategy.strategy.Shopowner;

/**
 * Create Date: 2017/11/19 
 * Description: 测试策略模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {

    public static void main(String[] args) {
        double price = 1000;
        Context context = new Context();
        context.setStrategy(new SaleMan());
        double v1 = context.calcPrice(price);
        System.out.println(context.getStrategy()+"给出的价格为:"+v1);

        context.setStrategy(new Shopowner());
        double v2 = context.calcPrice(price);
        System.out.println(context.getStrategy()+"给出的价格为:"+v2);


        context.setStrategy(new Manager());
        double v3 = context.calcPrice(price);
        System.out.println(context.getStrategy()+"给出的价格为:"+v3);
    }
}
