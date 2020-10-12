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
 * Description: 负责执行构建的导演
 * @author kiwipeach [1099501218@qq.com]
 */
public class Director {

    private Builder builder;

    public Product createProduct(Builder builder,String name){
        this.builder = builder;
        Product product = new Product(name);
        builder.buildHead(product);
        builder.buildBody(product);
        builder.buildFoot(product);
        return product;
    }

}
