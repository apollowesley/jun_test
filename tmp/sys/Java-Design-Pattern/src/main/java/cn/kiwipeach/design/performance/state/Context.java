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
 * Description: 执行环境
 * @author kiwipeach [1099501218@qq.com]
 */
public class Context {
    private QQState qqState;

    public QQState getQqState() {
        return qqState;
    }

    public void setQqState(QQState qqState) {
        this.qqState = qqState;
    }

    public void action(){
        if (qqState.equals(QQState.QQ_ONLINE)){
            System.out.println("用户在线状态");
        }else if (qqState.equals(QQState.QQ_BUSINSING)){
            System.out.println("用户忙碌状态");
        }else if (qqState.equals(QQState.QQ_LEAVE)){
            System.out.println("用户处于离线状态");
        }else {
            System.out.println("未知状态!");
        }
    }
}
