package com.site.auth.model;

import com.site.framework.base.BaseModel;

/**
 * 用户对象
 *
 */
public class User extends BaseModel {

    private static final long serialVersionUID = -1083711780724721275L;

    /** email **/
    private String email;

    /** 登录名 **/
    private String loginName;

    /** 用户名 **/
    private String userName;

    /** 密码 **/
    private String password;

    /** salt码 **/
    private String salt;

    public User() {
        super();
    }

    public User(String id, String email, String loginName, String userName, String password) {
        super();
        setId(id);
        this.email = email;
        this.loginName = loginName;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}