/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.builder;

import cn.kiwipeach.design.create.builder.entity.Product;
import cn.kiwipeach.design.create.builder.facade.Builder;

/**
 * Create Date: 2017/11/25 
 * Description: 测试构建模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        //大黄蜂构建器
        Builder builder = new ProductBuilder();
        Director director = new Director();
        Product product = director.createProduct(builder,"大黄蜂");
        System.out.println(product);
    }
}
