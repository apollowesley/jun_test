package com.mkfree.java_reflect;

/**
 * @author oyhk
 * @date 2019-04-23 18:01
 **/

public class User extends BaseDomain{

    @HbaseColumn(name = "account")
    private String account;
    @HbaseColumn(name = "age")
    private Integer age;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
