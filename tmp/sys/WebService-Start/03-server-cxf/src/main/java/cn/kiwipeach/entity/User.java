package cn.kiwipeach.entity;

/**
 * Create Date: 2017/11/12
 * Description: 用户实体类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class User {
    private String username;
    private String userpasswd;

    public User(String username, String userpasswd) {
        this.username = username;
        this.userpasswd = userpasswd;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpasswd() {
        return userpasswd;
    }

    public void setUserpasswd(String userpasswd) {
        this.userpasswd = userpasswd;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userpasswd='" + userpasswd + '\'' +
                '}';
    }
}
