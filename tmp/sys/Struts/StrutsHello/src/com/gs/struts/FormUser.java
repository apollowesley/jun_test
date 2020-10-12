package com.gs.struts;

import java.util.List;

/**
 * Created by WangGenshen on 3/4/16.
 */
public class FormUser {
    private String id;
    private String name;
    private String pwd;
    private String desc;
    private boolean accept;
    private String gender;
    private int age;
    private List<Address> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "FormUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", desc='" + desc + '\'' +
                ", accept=" + accept +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", addresses=" + addresses +
                '}';
    }
}

class Address {
    private int id;
    private String detail;

    public Address() {

    }

    public Address(int id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                '}';
    }
}
