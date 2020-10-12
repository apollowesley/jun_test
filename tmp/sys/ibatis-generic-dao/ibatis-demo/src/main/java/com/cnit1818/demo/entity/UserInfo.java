package com.cnit1818.demo.entity;

import java.util.Date;

/**
 * Created by mayong on 2016/4/23.
 */
public class UserInfo {

    private Long id;
    private String username;
    private String password;
    private Date optTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }
}
