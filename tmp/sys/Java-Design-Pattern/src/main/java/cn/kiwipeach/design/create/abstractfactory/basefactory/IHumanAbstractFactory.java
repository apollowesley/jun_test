/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.abstractfactory.basefactory;

import cn.kiwipeach.design.create.abstractfactory.baseentity.Cloth;
import cn.kiwipeach.design.create.abstractfactory.baseentity.Jewelry;
import cn.kiwipeach.design.create.abstractfactory.baseentity.Pants;

/**
 * Create Date: 2017/11/15 
 * Description: 人类服装搭配接口，规定每天不同的上衣、裤子、首饰
 * @author kiwipeach [1099501218@qq.com]
 */
public interface IHumanAbstractFactory {
    /**
     * 搭配上衣
     * @param target 搭配目标
     * @return 返回上衣
     * @throws Exception
     */
    Cloth chooseCloth(String target) throws  Exception;


    /**
     * 搭配裤子
     * @param target 搭配目标
     * @return 返回裤子
     * @throws Exception
     */
    Pants choosePants(String target) throws Exception;


    /**
     * 搭配首饰
     * @param target 搭配目标
     * @return 返回首饰
     * @throws Exception
     */
    Jewelry chooseJewelry(String target) throws Exception;




}
