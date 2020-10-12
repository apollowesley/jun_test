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
 * Description: 抽象类
 * @author kiwipeach [1099501218@qq.com]
 */
public abstract class AbstractTempleteMethod {

    private ITargetMethod iTargetMethod;

    public AbstractTempleteMethod(ITargetMethod iTargetMethod) {
        this.iTargetMethod = iTargetMethod;
    }

    public ITargetMethod getiTargetMethod() {
        return iTargetMethod;
    }


    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public abstract long recordMethod();
}
