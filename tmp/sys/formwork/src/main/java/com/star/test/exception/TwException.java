package com.star.test.exception;

import com.star.test.constant.FormConstant;
import lombok.Data;

@Data
public class TwException extends RuntimeException {

    private String errMsg = FormConstant.EXCEPTION_DEFAULT_MSG;
    private String errCode = FormConstant.EXCEPTION_CODE;

    public TwException() {
    }

    public TwException(String code, String msg) {
        this.errCode = code;
        this.errMsg = msg;
    }

    public TwException(String msg) {
        this.errMsg = msg;
    }


}
