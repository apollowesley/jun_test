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
 * Description: 调用者
 * @author kiwipeach [1099501218@qq.com]
 */
public class Invoker {
    private String name;

    public Invoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command sendCommand(Receiver receiver, Operations operations){
        System.out.println(name+"发送"+operations+"命令");
        return new Command(this,receiver,operations);
    }


}
