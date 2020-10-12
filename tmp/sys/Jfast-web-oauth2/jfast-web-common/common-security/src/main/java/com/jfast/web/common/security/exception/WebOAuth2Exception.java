package com.jfast.web.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfast.web.common.core.utils.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * WebOAuth2 异常
 * @author Pan Weilong
 * @date 2019/7/10 10:09
 * @description: 接口.
 */
@JsonSerialize(using = WebOAuthExceptionJacksonSerializer.class)
public class WebOAuth2Exception extends OAuth2Exception {

    private ResultCode resultCode;

    public WebOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public WebOAuth2Exception(String msg) {
        super(msg);
    }

    /**
     * 必须返回状态码200 否则前端只能在catch 中捕获该异常
     * @return
     */
    @Override
    public int getHttpErrorCode() {
        return HttpStatus.OK.value();
    }

    public WebOAuth2Exception(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
