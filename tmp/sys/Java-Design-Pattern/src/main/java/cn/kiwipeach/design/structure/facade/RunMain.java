/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.facade;

/**
 * Create Date: 2017/11/19 
 * Description: 模块设计模式测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        CustomerSwitch customerSwitch = new CustomerSwitch();
        ModelASwitch modelASwitch = new ModelASwitch();
        ModelBSwitch modelBSwitch = new ModelBSwitch();
        customerSwitch.getSwitchActionList().add(modelASwitch);
        customerSwitch.getSwitchActionList().add(modelBSwitch);
        customerSwitch.startUp();
        customerSwitch.shutDown();
        /**
         console result is:
         模块A开启....
         模块B开启....
         模块A关闭....
         模块B关闭....
         */
    }
}
