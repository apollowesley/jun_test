package com.cjlgb.design.common.oauth.dto;

import com.cjlgb.design.common.oauth.enums.GrantType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author WFT
 * @date 2020/6/14
 * description:
 */
@Setter
@Getter
@NoArgsConstructor
public class OauthParameter extends GrantParameter {

    /**
     * 认证方式
     */
    private GrantType grantType;

    /**
     * 客户端Id
     */
    @JsonProperty(value = "client_id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @JsonProperty(value = "client_secret")
    private String clientSecret;

    /**
     * 授权码,授权码模式必填
     */
    private String code;
}
