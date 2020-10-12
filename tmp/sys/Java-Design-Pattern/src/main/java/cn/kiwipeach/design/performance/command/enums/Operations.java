/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.command.enums;

/**
 * Create Date: 2017/11/23
 * Description: 操作命令
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public enum Operations {
    CMD_ADD("1001","添加"),
    CMD_DELETE("1002","删除"),
    CMD_REDO("1003","撤销"),
    CMD_COPY("1004","粘贴");
    private String code;
    private String value;
    Operations(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public Operations stateOf(String code) {
        if (code == null) {
            throw new NullPointerException("code can't be null");
        }
        for (Operations op : values()) {
            if(code.equals(op.code)){
                return op;
            }
        }
        return null;
    }

}
