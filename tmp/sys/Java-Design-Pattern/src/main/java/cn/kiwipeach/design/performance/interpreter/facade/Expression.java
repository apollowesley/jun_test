/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.interpreter.facade;

import cn.kiwipeach.design.performance.interpreter.Context;

/**
 * Create Date: 2017/11/25 
 * Description: 运算法则
 * @author kiwipeach [1099501218@qq.com]
 */
public interface Expression {

    public int calc(Context context);
}
