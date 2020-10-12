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
 * Description: 测试模板模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        TargetMethod targetMethod  = new TargetMethod();
        MethodTimeRecord methodTimeRecord = new MethodTimeRecord(targetMethod);
        methodTimeRecord.addData2List();
    }
}