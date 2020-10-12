package com.tinymood.bean.beanpostprocessor;

/**
 * spring 后置处理器BeanFactoryPostProcessor和BeanPostProcessor的用法和区别测试代码
 */
public class User {

    private String username;

    private String password;

    public User() {
        System.out.println("这是User的构造器");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
