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
 * Description: QQ状态
 * @author kiwipeach [1099501218@qq.com]
 */
public enum QQState {
    QQ_ONLINE("1001","在线"),
    QQ_LEAVE("1002","离线"),
    QQ_BUSINSING("1003","忙碌");
    private String code;
    private String value;
    QQState(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public QQState stateOf(String code) {
        if (code == null) {
            throw new NullPointerException("code can't be null");
        }
        for (QQState state : values()) {
            if(code.equals(state.code)){
                return state;
            }
        }
        return null;
    }
}
