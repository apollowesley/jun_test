package com.zb.common.vo;

import com.zb.common.enums.Status;

/**
 * 
 * 
 * 创建日期：2016年8月4日 下午2:42:13 操作用户：zhoubang
 * 
 */
public class PermissionVo {

    private Long id;

    private String name;// 名称
    private String code;// 权限代码
    private String description;// 描述
    private Status status;// 状态
    private String parentName;// 父级节点

    private String statusName;
    
    
    public String getStatusName() {
        return status.getDescription();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
