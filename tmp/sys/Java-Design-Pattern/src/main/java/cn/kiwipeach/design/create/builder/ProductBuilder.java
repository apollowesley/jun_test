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
import cn.kiwipeach.design.create.builder.entity.ProductBody;
import cn.kiwipeach.design.create.builder.entity.ProductFoot;
import cn.kiwipeach.design.create.builder.entity.ProductHead;
import cn.kiwipeach.design.create.builder.facade.Builder;

/**
 * Create Date: 2017/11/25 
 * Description: 变形金刚变形器
 * @author kiwipeach [1099501218@qq.com]
 */
public class ProductBuilder implements Builder {

    @Override
    public void buildHead(Product product) {
        System.out.println(product.getName()+"构建头部开始");
        try {
            System.out.println(product.getName()+"头部构建中....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        product.setProductHead(new ProductHead());
        System.out.println(product.getName()+"构建头部结束");
    }

    @Override
    public void buildBody(Product product) {
        System.out.println(product.getName()+"构建身体开始");
        try {
            System.out.println(product.getName()+"身体构建中....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        product.setProductBody(new ProductBody());
        System.out.println(product.getName()+"构建身体结束");

    }

    @Override
    public void buildFoot(Product product) {
        System.out.println(product.getName()+"构建下体开始");
        try {
            System.out.println(product.getName()+"下体构建中....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        product.setProductFoot(new ProductFoot());
        System.out.println(product.getName()+"构建下体结束");

    }
}
