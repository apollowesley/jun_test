package com.handy.captcha;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author handy
 * @Description: {验证码模版参数}
 * @date 2019/8/25 14:04
 */
@Getter
@AllArgsConstructor
public enum CaptchaEnum {
    TEMPLATE_REG(1L, "注册验证码", "rapid", "SMS_172887332"),
    TEMPLATE_LOGIN(2L, "登录验证码", "rapid", "SMS_172887348"),
    TEMPLATE_CHANGE(3L, "修改密码验证码", "rapid", "SMS_172887348");

    private Long id;
    private String name;
    private String signName;
    private String templateCode;
}
