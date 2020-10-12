package cn.kiwipeach.jvm.test; /**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */

/**
 * Create Date: 2017/11/15 
 * Description: 植物
 * @author kiwipeach [1099501218@qq.com]
 */
public class Plant {
    static {
        System.out.println("Plant的静态代码块...");
    }

    public Plant() {
        System.out.println("cn.kiwipeach.jvm.test.Plant Constructor...");
    }

    {
        System.out.println("Plant的非静态代码块...");
    }

}
