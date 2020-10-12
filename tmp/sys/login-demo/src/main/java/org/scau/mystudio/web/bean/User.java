package org.scau.mystudio.web.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author jinglun
 * @create 2016-09-10 11:27
 */
@Entity
public class User {
    private int userId;
    private String userName;
    private String password;

    @Id
    @Column(name = "userId", nullable = false, insertable = true, updatable = true)
    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    @Basic
    @Column(name = "userName", nullable = true, insertable = true, updatable = true, length = 255)
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
