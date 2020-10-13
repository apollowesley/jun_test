package com.zb.common.qo;

import com.zb.common.enums.UserStatus;

public class UserQo {

    private Long roleId;// 角色id
    private String realName;// 姓名
    private String userName;// 账户名
    private UserStatus status;// 状态
    private Long userId;
    private String password;// 密码
    private Long[] roleIds;// 用户拥有的角色id集合

    private boolean haveUserListPermission;// 是否拥有查看用户列表权限

    
    public boolean getHaveUserListPermission() {
        return haveUserListPermission;
    }

    public void setHaveUserListPermission(boolean haveUserListPermission) {
        this.haveUserListPermission = haveUserListPermission;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
