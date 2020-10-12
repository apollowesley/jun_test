/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.interpreter;

import cn.kiwipeach.design.performance.interpreter.impl.Div;
import cn.kiwipeach.design.performance.interpreter.impl.Plus;

/**
 * Create Date: 2017/11/25
 * Description: 测试解释器模式
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        //1+2=3
        int result1 = new Plus().calc(new Context(1, 2));
        System.out.println(result1);
        //1+(2+10/2)=8
        int result2 = new Plus().calc(new Context(1, new Plus().calc(new Context(2, new Div().calc(new Context(10, 2))))));
        System.out.println(result2);
        /**
         console result is:
         3
         8
         */
    }
}
