/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.chainofresponsibility;

/**
 * Create Date: 2017/11/22 
 * Description: 测试责任链模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        ConcreteHandler handler1 = new ConcreteHandler("小明");
        ConcreteHandler handler2 = new ConcreteHandler("小红");
        ConcreteHandler handler3 = new ConcreteHandler("小张");
        handler1.setiHandler(handler2);
        handler2.setiHandler(handler3);
        handler1.operation();
    }
}
