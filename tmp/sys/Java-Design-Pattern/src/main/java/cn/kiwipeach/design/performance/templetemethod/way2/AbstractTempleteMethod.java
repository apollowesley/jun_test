/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.templetemethod.way2;

/**
 * Create Date: 2017/11/19 
 * Description: 抽象类
 * @author kiwipeach [1099501218@qq.com]
 */
public abstract class AbstractTempleteMethod {

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public long recordMethod(){
        long startTime = getCurrentTime();
        targetMethod();
        long endTime  = getCurrentTime();
        return endTime - startTime;
    }

    public abstract void targetMethod();


}
