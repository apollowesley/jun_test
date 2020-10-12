package com.team.blogs.common.response;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @Author: xiaokai
 * @Description: 返回对象实体
 * @Date: 2019/7/9
 * @Version: 1.0
 */
public class RetResult<T> implements Serializable {

    private static final long serialVersionUID = 3758864789222317092L;

    public int code;

    private String msg;

    private T data;

    public RetResult<T> setCode(RetCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
