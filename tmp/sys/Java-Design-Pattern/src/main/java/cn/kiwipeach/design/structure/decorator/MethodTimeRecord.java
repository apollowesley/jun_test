/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.decorator;

/**
 * Create Date: 2017/11/15 
 * Description: 计时器类
 * @author kiwipeach [1099501218@qq.com]
 */
public class MethodTimeRecord implements IMethod {

    private IMethod targetMethod;

    public MethodTimeRecord(IMethod iMethod) {
        this.targetMethod = iMethod;
    }

    @Override
    public void addData2List() {
        long startTime = System.currentTimeMillis();
        targetMethod.addData2List();
        long endTime = System.currentTimeMillis();
        System.out.println("共耗时:"+(endTime-startTime)+"毫秒");
    }
}
