package com.gs.struts;

/**
 * Created by WangGenshen on 2/26/16.
 * Struts Action是线程安全的,每一个请求都会创建一个新的action实例
 */
public class UserAction {

    private User user;

    private String result;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public String login() {
        return "info";
    }

    public String save() {
        result = "save success";
        return "success";
    }

    public String update() {
        result = "update success";
        return "success";
    }

    public String delete() {
        result = "delete success";
        return "success";
    }

    public String query() {
        result = "query success";
        return "success";
    }
}
