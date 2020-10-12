/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.factorymethod;

/**
 * Create Date: 2017/11/14 
 * Description: 测试FactoryMehtod模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        //1）制作夹克衫
        IClothProducer producerFactory = new JacketFactory();
        Cloth jack = producerFactory.produce();
        System.out.println(jack.getClass().getName());
        //2）制作裤子
        producerFactory = new PantsFactory();
        Cloth pants = producerFactory.produce();
        System.out.println(pants.getClass().getName());
        //3）若存在制作鞋子的场景，那么只需要创建个鞋子工厂和鞋子实体类就行了（可拔插）
    }
}
