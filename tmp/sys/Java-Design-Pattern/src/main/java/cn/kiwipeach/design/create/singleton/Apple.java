/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.singleton;

/**
 * Create Date: 2017/11/14 
 * Description: 苹果实体类
 * @author kiwipeach [1099501218@qq.com]
 */
public class Apple {
    public Apple() {
        System.out.println("单例Apple实例化（我不能出现两次）");
    }
}
