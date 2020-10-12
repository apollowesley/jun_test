package com.slavic.hors.admin.api.dto.portal.user.request;

import com.slavic.veles.base.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends BaseRequest {

    @NotNull(message = "登录用户名不允许为空")
    private String username;

    @NotNull(message = "登录密码不允许为空")
    private String password;

}
