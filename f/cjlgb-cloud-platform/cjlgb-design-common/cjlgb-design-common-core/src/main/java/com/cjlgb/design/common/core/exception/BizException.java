package com.cjlgb.design.common.core.exception;

import lombok.Getter;

/**
 * @author WFT
 * @date 2020/5/6
 * description: 自定义业务异常
 */
@Getter
public class BizException extends RuntimeException{

    /**
     * Http状态码
     */
    private int code;

    /**
     * 数据
     */
    private Object data;

    public BizException(int code,String message) {
        super(message);
        this.code = code;
    }

    public BizException(int code, String message, Object data) {
        this(code,message);
        this.data = data;
    }
}
