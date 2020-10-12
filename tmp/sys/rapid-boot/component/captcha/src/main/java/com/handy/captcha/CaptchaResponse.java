package com.handy.captcha;

import lombok.Data;

import java.io.Serializable;

/**
 * @author handy
 * @Description: {验证码返回参数}
 * @date 2019/8/25 13:54
 */
@Data
public class CaptchaResponse implements Serializable {
    private String Message;
    private String RequestId;
    private String BizId;
    private String Code;
}
