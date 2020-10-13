package org.iartisan.admin.template.authentication.service.entity;

import java.util.Date;

/**
 * @author King
 * @since 2018/3/1
 */
public class RoleEntity {

    private String permissions;

    private String roleName;

    private String roleId;

    private Date createTime;

    private boolean isOwn = false;//是否拥有


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }
}
