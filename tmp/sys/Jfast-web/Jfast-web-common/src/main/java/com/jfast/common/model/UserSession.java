package com.jfast.common.model;


import com.jfast.common.utils.ObjectUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 22:09
 */
public class UserSession implements Serializable {

    private String sessionId;
    private Map userMap; // 存储管理员信息
    private List<Map> roleList;
    private List<Map> menuList;
    private Set<String> permissionList = new HashSet<>();


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UserSession(Map userMap) {
        this.userMap = userMap;
    }

    public void addPermission(String permission) {
        permissionList.add(permission);
    }

    public void setPermissionList(Set<String> permissionList) {
        this.permissionList = permissionList;
    }

    public Set<String> getPermissionList() {
        return permissionList;
    }

    public Integer getUserId() {
        if (ObjectUtils.isEmpty(userMap)) {
            return null;
        }
        Number n = (Number)userMap.get("id");
        return n != null ? n.intValue() : null;
    }

    public List<Map> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Map> roleList) {
        this.roleList = roleList;
    }

    public List<Map> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Map> menuList) {
        this.menuList = menuList;
    }

    public Map getUserMap() {
        return userMap;
    }

    public void setUserMap(Map userMap) {
        this.userMap = userMap;
    }
}
