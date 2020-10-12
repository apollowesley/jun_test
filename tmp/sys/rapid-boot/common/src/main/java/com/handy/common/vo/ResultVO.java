package com.handy.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 15:18
 */
@Data
public class ResultVO implements Serializable {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String ERROR = "error";
    public static final String SUCCESS_CODE = "1";
    public static final String FAILURE_CODE = "-1";
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "返回消息")
    private String msg;
    @ApiModelProperty(value = "返回参数")
    private Object data;

    private ResultVO() {
        this.status = "success";
        this.code = "1";
    }

    private ResultVO(Object data) {
        this.status = "success";
        this.code = "1";
        this.data = data;
    }

    private ResultVO(String status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    private ResultVO(String status, String code, Object data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public ResultVO(String status, String code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVO success() {
        return new ResultVO();
    }

    public static ResultVO success(String msg) {
        return new ResultVO("success", "1", msg);
    }

    public static ResultVO success(String msg, Object data) {
        return new ResultVO("success", "1", msg, data);
    }

    public static ResultVO data(Object data) {
        return new ResultVO(data);
    }

    public static ResultVO failure(String status, String code, String msg) {
        return new ResultVO(status, code, msg);
    }

    public static ResultVO failure(String msg) {
        return new ResultVO("failure", "-1", msg);
    }

    public static ResultVO failure(String msg, Object data) {
        return new ResultVO("failure", "-1", msg, data);
    }

    public static ResultVO error(String msg) {
        return new ResultVO("error", "-1", msg);
    }

    public static ResultVO data(String status, String code, Object data) {
        return new ResultVO(status, code, data);
    }

    public static ResultVO data(String status, String code, String msg, Object data) {
        return new ResultVO(status, code, msg, data);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}