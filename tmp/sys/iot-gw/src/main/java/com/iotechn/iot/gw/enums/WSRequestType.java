package com.iotechn.iot.gw.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-12-24
 * Time: 下午12:04
 */
public enum WSRequestType {
    HEART(0,"心跳"),
    PARAM(1,"参数");
    private int type;
    private String text;

    WSRequestType(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
