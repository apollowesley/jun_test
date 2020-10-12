/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.prototype;

import java.io.Serializable;

/**
 * Create Date: 2017/11/15 
 * Description: 专业
 * @author kiwipeach [1099501218@qq.com]
 */
public class Deptment implements Cloneable,Serializable {
    private String id;
    private String name;

    public Deptment() {
    }

    public Deptment(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object deepClone() throws CloneNotSupportedException {
        return super.clone();
    }
}
