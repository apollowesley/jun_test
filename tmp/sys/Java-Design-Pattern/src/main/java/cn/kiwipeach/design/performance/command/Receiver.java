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
 * Description: 接收者
 * @author kiwipeach [1099501218@qq.com]
 */
public class Receiver {
    private String name;

    public Receiver(String name) {
        this.name = name;
    }

    public void exec(Command command){
        System.out.println(name+"执行操作代号["+command.getOperations().getCode()+"]:"+command.getOperations());
    }

    public String getName() {
        return name;
    }

}
