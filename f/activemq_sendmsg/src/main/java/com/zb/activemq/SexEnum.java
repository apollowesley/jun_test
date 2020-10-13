package com.zb.activemq;

public enum SexEnum {

    MAN(0, "男"), WOMEN(1, "女");

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private SexEnum(int code, String description) {
        this.code = new Integer(code);
        this.description = description;
    }
}
