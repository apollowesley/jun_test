package com.xinhuo.demo.common.model;

import com.alibaba.fastjson.JSONObject;

/**
 * author 张新林
 * 时间 2019/6/16 16:12
 * 描述
 */
public class ResponseMsg {
    protected int code;
    protected String message;
    protected Object data;

    public ResponseMsg(){
    }

    public ResponseMsg(Object data){
        this.data = data;
    }

    public ResponseMsg(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
