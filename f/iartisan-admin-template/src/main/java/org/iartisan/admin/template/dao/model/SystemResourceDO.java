package org.iartisan.admin.template.dao.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

/**
 * system_resource 表模型
 *
 * @author King
 */
@Alias("systemResource")
@TableName(value = "system_resource")
public class SystemResourceDO {

    /**
     * 列名: RESOURCE_ID
     * 备注: 主键
     */
    @TableId("RESOURCE_ID")
    private String resourceId;

    /**
     * 列名: RESOURCE_NAME
     * 备注: 资源名称
     */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /**
     * 列名: RESOURCE_PERMISSION
     * 备注: 资源权限编码 resource:菜单id:资源id
     */
    @TableField("RESOURCE_PERMISSION")
    private String resourcePermission;

    /**
     * 列名: MENU_ID
     * 备注: 对应的MENU_ID
     */
    @TableField("MENU_ID")
    private String menuId;

    /**
     * 列名: CREATE_TIME
     * 备注: 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 列名: UPDATE_TIME
     * 备注: 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePermission() {
        return resourcePermission;
    }

    public void setResourcePermission(String resourcePermission) {
        this.resourcePermission = resourcePermission;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}