package com.jfast.web.common.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/17 20:16
 */
public class SystemSecurityUser extends User {

    private Integer id;
    private String encrypt;
    private boolean isSuper;
    private List<Map> roleList; // 角色集合
    private List<Map> menuList; // 菜单集合

    public SystemSecurityUser(String username, String password, String encrypt,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.encrypt = encrypt;
    }

    public List<Map> getRoleList() {
        return roleList;
    }

    public void setMenuList(List<Map> menuList) {
        this.menuList = menuList;
    }

    public void setRoleList(List<Map> roleList) {
        this.roleList = roleList;
    }

    public List<Map> getMenuList() {
        return menuList;
    }

    public void setSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }


}
