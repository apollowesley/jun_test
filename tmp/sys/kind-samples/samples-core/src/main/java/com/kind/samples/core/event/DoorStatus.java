package com.kind.samples.core.event;

/**
 * Created by cary on 2016/12/18.
 */
public enum DoorStatus {
    OPEN("OPEN", "开门"),
    CLOSE("CLOSE", "关门");

    private String code;
    private String desc;

    DoorStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
