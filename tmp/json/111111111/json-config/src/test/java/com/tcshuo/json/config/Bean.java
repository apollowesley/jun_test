/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tengda
 */
public class Bean {
    
    private List<User> users = new ArrayList<User>();

    private String name;

    private BeanType type;

    private Date date = new Date();
    
    private Integer[] targets ;
    
    private boolean b1 = true;
    
    private Boolean b2 = true;
    
    private int int1 = 100;
    
    private Integer int2 = 100;
    

    public Bean() {
    }

    public Bean(String name, BeanType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanType getType() {
        return type;
    }

    public void setType(BeanType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer[] getTargets() {
        return targets;
    }

    public void setTargets(Integer[] targets) {
        this.targets = targets;
    }

    public boolean isB1() {
        return b1;
    }

    public void setB1(boolean b1) {
        this.b1 = b1;
    }

    public Boolean getB2() {
        return b2;
    }

    public void setB2(Boolean b2) {
        this.b2 = b2;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }
    
    
    

}
