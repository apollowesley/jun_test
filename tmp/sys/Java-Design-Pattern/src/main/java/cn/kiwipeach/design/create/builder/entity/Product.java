/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.builder.entity;

/**
 * Create Date: 2017/11/25 
 * Description: 变形金刚对象
 * @author kiwipeach [1099501218@qq.com]
 */
public class Product {
    private String name;

    private ProductHead productHead;
    private ProductBody productBody;
    private ProductFoot productFoot;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductHead getProductHead() {
        return productHead;
    }

    public void setProductHead(ProductHead productHead) {
        this.productHead = productHead;
    }

    public ProductBody getProductBody() {
        return productBody;
    }

    public void setProductBody(ProductBody productBody) {
        this.productBody = productBody;
    }

    public ProductFoot getProductFoot() {
        return productFoot;
    }

    public void setProductFoot(ProductFoot productFoot) {
        this.productFoot = productFoot;
    }
}
