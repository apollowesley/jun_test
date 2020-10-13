package org.iartisan.admin.template.dao.model;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

/**
 * system_role_permission 表模型
 *
 * @author King
 */
@Alias("systemRolePermission")
@TableName(value = "system_role_permission")
public class SystemRolePermissionDO {

    /**
     * 列名: ROLE_ID
     * 备注: 角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 列名: PERMISSION_ID
     * 备注: 资源id
     */
    @TableField("PERMISSION_ID")
    private String permissionId;

    /**
     * 列名: PERMISSION_TYPE
     * 备注: m:菜单 r:resource
     */
    @TableField("PERMISSION_TYPE")
    private String permissionType;

    /**
     * 列名: CREATE_TIME
     * 备注: 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    private List<String> roleIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}