/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.state;

/**
 * Create Date: 2017/11/23 
 * Description: 状态模式测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        Context context = new Context();
        context.setQqState(QQState.QQ_ONLINE);
        context.action();
        context.setQqState(QQState.QQ_LEAVE);
        context.action();
    }
}
