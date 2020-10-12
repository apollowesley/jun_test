package cn.backflow.admin.entity;


import cn.backflow.admin.entity.base.BaseEntity;

public class RolePermission extends BaseEntity {

    public Integer roleId;
    public Integer permId;
    public String perm;

    public RolePermission(Integer roleId, Integer permId, String perm) {
        this.roleId = roleId;
        this.permId = permId;
        this.perm = perm;
    }
}