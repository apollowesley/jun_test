package com.slavic.hors.admin.api.dto.portal.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateRequest {

    private Long id;

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "真实姓名不允许为空")
    private String realName;

    private String nickName;

    private String userPhone;

    private Long avatarUrl;

    private String userEmail;

    @NotNull(message = "用户状态不允许为空")
    private Integer userStatus;

}
