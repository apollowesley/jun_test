/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.templetemethod;

import cn.kiwipeach.design.performance.templetemethod.way1.AbstractTempleteMethod;
import cn.kiwipeach.design.performance.templetemethod.way1.ITargetMethod;
import cn.kiwipeach.design.performance.templetemethod.way1.MyTargetMethod;
import cn.kiwipeach.design.performance.templetemethod.way1.TempleteMethodImpl;

/**
 * Create Date: 2017/11/19 
 * Description: 模板方法测试i
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain1 {
    public static void main(String[] args) {
        ITargetMethod iTargetMethod = new MyTargetMethod();
        AbstractTempleteMethod abstractTempleteMethod = new TempleteMethodImpl(iTargetMethod);
        long wastTime = abstractTempleteMethod.recordMethod();
        System.out.println(wastTime);
    }
}
