package com.gs.struts;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Created by WangGenshen on 4/8/16.
 */
public class Convertion1Action extends ActionSupport {

    private List<User> users; // 可直接从页面提交多个数据过来,页面中name值使用users[0].name, users[1].name的方式

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String execute() throws Exception {
        for (User user : users) {
            System.out.println(user.getName());
        }
        return SUCCESS;
    }

    public String toPage() {
        return INPUT;
    }
}
