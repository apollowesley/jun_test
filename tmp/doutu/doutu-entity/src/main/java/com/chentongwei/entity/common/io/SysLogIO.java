package com.chentongwei.entity.common.io;

import java.io.Serializable;

/**
 * 保存或更新IO
 *
 * @author TongWei.Chen 2017-06-01 20:04:53
 */
public class SysLogIO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
    /** 操作人id */
    private Long operatorId;
    /** 操作人 */
    private String operator;
    /** 一级菜单 */
    private String menu;
    /** 子菜单 */
    private String subMenu;
    /** 操作内容 */
    private String content;
    /** 客户端ip */
    private String clientIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(String subMenu) {
        this.subMenu = subMenu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}