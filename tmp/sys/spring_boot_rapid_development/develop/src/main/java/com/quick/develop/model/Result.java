package com.quick.develop.model;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result<>(200, "success", null);
    }

    public static Result success(Object data) {
        return new Result<>(200, "success", data);
    }

    public static Result failed(Integer code,String message){
        return new Result<>(code, message, null);
    }

    public static Result failed(Integer code,String message,Object data){
        return new Result<>(code, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
