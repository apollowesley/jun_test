/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.command;

import cn.kiwipeach.design.performance.command.enums.Operations;

/**
 * Create Date: 2017/11/23 
 * Description: 测试命令模式
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        Invoker invoker = new Invoker("小红");
        Receiver receiver = new Receiver("小明");
        Command command = invoker.sendCommand(receiver, Operations.CMD_ADD);
        receiver.exec(command);
        /**
         console result is:
         小红发送CMD_ADD命令
         小明执行操作代号[1001]:CMD_ADD
         */
    }
}
