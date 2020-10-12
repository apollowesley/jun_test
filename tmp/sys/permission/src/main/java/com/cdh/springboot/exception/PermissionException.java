package com.cdh.springboot.exception;

import com.cdh.springboot.common.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionException extends RuntimeException {

    private Integer code;

    public PermissionException(ResponseCode responseCodeEnum) {
        this(responseCodeEnum.getCode(),responseCodeEnum.getMsg());
    }

    public PermissionException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
