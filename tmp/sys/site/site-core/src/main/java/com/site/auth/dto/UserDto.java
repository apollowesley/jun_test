package com.site.auth.dto;

import com.site.auth.model.User;

/**
 * 用户DTO
 *
 */
public class UserDto extends User {

    private static final long serialVersionUID = 6944437639641000530L;

    // 用于登录，即登录账号名
    private String username;

    // 确认密码
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
