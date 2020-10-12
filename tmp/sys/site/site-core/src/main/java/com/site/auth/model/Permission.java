package com.site.auth.model;

import com.site.framework.base.BaseModel;

/**
 * 菜单许可
 */
public class Permission extends BaseModel {

    private static final long serialVersionUID = -7141829387338999544L;

    /** 菜单名称 **/
    private String menuName;

    /** 菜单编码 **/
    private String menuCode;

    /** URL **/
    private String url;

    /** 菜单等级 **/
    private Integer lev;

    /** 显示顺序 **/
    private Integer sort;

    /** 父菜单ID **/
    private String parentId;

    /** 所属业务系统 **/
    private String businessSystem;

    /** 备注 **/
    private String remark;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
