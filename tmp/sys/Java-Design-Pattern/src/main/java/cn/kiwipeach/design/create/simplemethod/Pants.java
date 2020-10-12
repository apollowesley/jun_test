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
 * Create Date: 2017/11/14 
 * Description: 裤子
 * @author kiwipeach [1099501218@qq.com]
 */
public class Pants extends Cloth {
    /**
     * 裤子编号
     */
    private String id;
    /**
     * 裤子价格
     */
    private double price;

    /**
     * 裤子类型
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
