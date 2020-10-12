/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.simplemethod;

/**
 * Create Date: 2017/11/15 
 * Description: 普通工厂类
 * @author kiwipeach [1099501218@qq.com]
 */
public class ClothFactory implements IClothProducer{
    @Override
    public Cloth produce(String target) throws Exception {
        /**
         * 这里使用反射，比枚举或者if好多了（增强版简单工厂）
         */
        return (Cloth) Class.forName(target).newInstance();
    }
}
