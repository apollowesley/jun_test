/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.interpreter.impl;

import cn.kiwipeach.design.performance.interpreter.Context;
import cn.kiwipeach.design.performance.interpreter.facade.Expression;

/**
 * Create Date: 2017/11/25 
 * Description: 乘法
 * @author kiwipeach [1099501218@qq.com]
 */
public class Multi implements Expression {
    @Override
    public int calc(Context context) {
        return context.getNumber1()*context.getNumber2();
    }
}
