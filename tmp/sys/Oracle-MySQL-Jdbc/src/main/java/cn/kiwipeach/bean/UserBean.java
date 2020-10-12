/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.bean;

/**
 * Create Date: 2017/12/06 
 * Description: 测试实例Bean
 * @author kiwipeach [1099501218@qq.com]
 */
public class UserBean {
    private String id;
    String name;
    protected int age;
    public String email;

    public UserBean() {
    }

    public UserBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private UserBean(String id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    private void method1(){}

    void method2(){}

    protected void method3(){}

    public  void method4(){}

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
