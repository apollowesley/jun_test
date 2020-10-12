package com.handy.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/3 16:37
 */
@Getter
@AllArgsConstructor
public enum LogLoginCategoryEnum {

    REGISTER_LOGIN(0L, "注册登录"),
    PASSWORD_LOGIN(1L, "密码登录"),
    CAPTCHA_LOGIN(2L, "验证码登录");

    private Long categoryId;
    private String categoryName;
}
