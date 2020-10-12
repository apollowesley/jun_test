package com.handy.captcha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author handy
 * @Description: {验证码参数}
 * @date 2019/8/25 13:54
 */
@Data
public class CaptchaParam implements Serializable {
    @ApiModelProperty(value = "接收信息的手机号")
    private String phoneNumbers;
    @ApiModelProperty(value = "短信签名名称。请在控制台签名管理页面签名名称一列查看。")
    private String signName;
    @ApiModelProperty(value = "短信模板ID。请在控制台模板管理页面模板CODE一列查看。")
    private String templateCode;
    @ApiModelProperty(value = "短信模板变量对应的实际值，JSON格式。例如:{\"code\":\"123456\"}")
    private String templateParam;
}
