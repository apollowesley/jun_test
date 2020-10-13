package org.iartisan.admin.template.authentication.service.entity;

import java.util.Date;

/**
 * @author King
 * @since 2018/3/1
 */
public class ResourceEntity {

    private String id;

    private String resourceName;

    private String resourcePermission;

    private Date createTime;

    private String menuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
