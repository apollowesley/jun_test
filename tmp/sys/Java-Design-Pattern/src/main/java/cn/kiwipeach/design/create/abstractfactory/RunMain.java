/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.abstractfactory;

import cn.kiwipeach.design.create.abstractfactory.baseentity.Cloth;
import cn.kiwipeach.design.create.abstractfactory.baseentity.Jewelry;
import cn.kiwipeach.design.create.abstractfactory.baseentity.Pants;
import cn.kiwipeach.design.create.abstractfactory.basefactory.IHumanAbstractFactory;

/**
 * Create Date: 2017/11/15 
 * Description: 测试抽象工厂模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {

    public static void main(String[] args) throws Exception {
        IHumanAbstractFactory factory = new NormalDressFactory();
        Cloth normalCloth = factory.chooseCloth(null);
        Pants normalPants = factory.choosePants(null);
        Jewelry normalJewelry = factory.chooseJewelry(null);
        System.out.println("-------普通装备---------");
        System.out.println(normalCloth);
        System.out.println(normalPants);
        System.out.println(normalJewelry);
        factory  = new HighDressFactory();
        System.out.println("-------高级装备--------- ");
        factory = new HighDressFactory();
        Cloth highCloth = factory.chooseCloth(null);
        Pants highPants = factory.choosePants(null);
        Jewelry highJewelry = factory.chooseJewelry(null);
        System.out.println(highCloth);
        System.out.println(highPants);
        System.out.println(highJewelry);

    }
}
