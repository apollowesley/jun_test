package com.cdh.model;

import lombok.Data;

import java.util.Objects;

public enum UserType {
    ADMIN("系统管理员","admin"),TEACHER("教师","teacher"),STUDENT("学生","student");

    private String name;
    private String code;

    UserType(String name,String code){
         this.name = name;
         this.code = code;
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

    @Override
    public String toString() {
        return this.name;
    }
}
