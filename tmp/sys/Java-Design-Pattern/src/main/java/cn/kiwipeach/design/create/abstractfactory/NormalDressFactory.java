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
import cn.kiwipeach.design.create.abstractfactory.entity.Eyelashes;
import cn.kiwipeach.design.create.abstractfactory.entity.Jeans;
import cn.kiwipeach.design.create.abstractfactory.entity.Shirt;

/**
 * Create Date: 2017/11/15 
 * Description: 普通装扮:牛仔裤+T恤衫+假睫毛
 * @author kiwipeach [1099501218@qq.com]
 */
public class NormalDressFactory implements IHumanAbstractFactory{
    @Override
    public Cloth chooseCloth(String target) throws Exception {
        if(target!=null){
            return (Cloth) Class.forName(target).newInstance();
        }
        return new Shirt();
    }

    @Override
    public Pants choosePants(String target) throws Exception {
        if(target!=null){
            return (Pants) Class.forName(target).newInstance();
        }
        return new Jeans();
    }

    @Override
    public Jewelry chooseJewelry(String target) throws Exception {
        if(target!=null){
            return (Jewelry) Class.forName(target).newInstance();
        }
        return new Eyelashes();
    }
}
