/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.templetemethod.way1;

/**
 * Create Date: 2017/11/19 
 * Description: 需要测试计算时间的目标方法
 * @author kiwipeach [1099501218@qq.com]
 */
public class TempleteMethodImpl extends AbstractTempleteMethod {

    public TempleteMethodImpl(ITargetMethod iTargetMethod) {
        super(iTargetMethod);
    }

    @Override
    public long recordMethod() {
        long startTime = getCurrentTime();
        getiTargetMethod().invokeTarget();
        long endTime  = getCurrentTime();
        return endTime -startTime;
    }
}
