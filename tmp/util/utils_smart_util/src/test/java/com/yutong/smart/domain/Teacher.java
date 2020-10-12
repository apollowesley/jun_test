package com.yutong.smart.domain;

import java.util.Date;


public class Teacher {

    private String id;

    private String sex;

    private String name;

    private Date dob;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getDob() {
        return dob;
    }


    public void setDob(Date dob) {
        this.dob = dob;
    }

}
