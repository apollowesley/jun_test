package com.cjlgb.design.common.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author WFT
 * @date 2020/7/19
 * description:
 */
@Getter
@Setter
@NoArgsConstructor
public class GrantParameter implements java.io.Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * openId
     */
    private Long openId;

}
