package com.site.auth.model;

import com.site.framework.base.BaseModel;

/**
 * 用户角色关系
 */
public class UserRole extends BaseModel {

    private static final long serialVersionUID = -56720255622342923L;

    /** 用户ID **/
    private String userId;

    /** 角色ID **/
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
