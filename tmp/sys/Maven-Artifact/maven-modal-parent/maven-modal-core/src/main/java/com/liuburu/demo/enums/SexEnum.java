package com.liuburu.demo.enums;

/**
 * Create Date: 2017/11/09
 * Description: 性别枚举
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public enum SexEnum {
    MAN("1","男"),
    FEMAN("0","女"),
    OTHER("-1","女");
    ;
    private String code;
    private String value;

    SexEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
