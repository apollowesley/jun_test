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
 * Description: 命令
 * @author kiwipeach [1099501218@qq.com]
 */
public class Command {
    private String fromWho;
    private String toWho;
    private Operations operations;



    public Command(Invoker invoker, Receiver receiver, Operations operations) {
        this.fromWho = invoker.getName();
        this.toWho = receiver.getName();
        this.operations = operations;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }
}
