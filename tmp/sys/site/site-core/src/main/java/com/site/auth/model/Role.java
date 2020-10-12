package com.site.auth.model;

import com.site.framework.base.BaseModel;

/**
 * 角色
 *
 */
public class Role extends BaseModel {

    private static final long serialVersionUID = -6982490361440305761L;

    /** 角色名称 **/
    private String roleName;

    /** 编码 **/
    private String roleCode;

    /** 父角色ID **/
    private String parentId;

    /** 所属业务系统 **/
    private String businessSystem;

    /** 备注 **/
    private String remark;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBusinessSystem() {
        return businessSystem;
    }

    public void setBusinessSystem(String businessSystem) {
        this.businessSystem = businessSystem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
