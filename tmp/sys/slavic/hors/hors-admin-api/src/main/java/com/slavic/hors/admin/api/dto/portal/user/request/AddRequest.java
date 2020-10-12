package com.slavic.hors.admin.api.dto.portal.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddRequest {

    private Long id;

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "真实姓名不允许为空")
    private String realName;

    @NotNull(message = "密码不允许为空")
    private String password;

    private String nickName;

    private String userPhone;

    private String avatarUrl;

    private String userEmail;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    @NotNull(message = "用户状态不允许为空")
    private Object userStatus;

    private Integer horsVersion = 0;

}