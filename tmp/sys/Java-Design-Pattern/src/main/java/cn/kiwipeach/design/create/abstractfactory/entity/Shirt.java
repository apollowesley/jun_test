/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.abstractfactory.entity;

import cn.kiwipeach.design.create.abstractfactory.baseentity.Cloth;

/**
 * Create Date: 2017/11/14 
 * Description: 夹克衫
 * @author kiwipeach [1099501218@qq.com]
 */
public class Shirt extends Cloth {
    /**
     * 夹克编号
     */
    private String id;
    /**
     * 夹克价格
     */
    private double price;

    /**
     * 夹克类型
     */
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
