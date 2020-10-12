package com.markbro.dzd.sso.core.user;

import java.util.Map;

/**
 * 注意该实体类和SsoUser的不同，在于使用场景不同。该实体类侧重于调用单点登录的外部系统登录用户信息传递到sso
 * 而SsoUser实体类的信息只在sso系统内部传递信息。
 * SsoUserInfo 通常被用于外部系统用户登录校验通过之后等到的登录用户信息，先转换为SsoUserInfo然后在进入sso系统执行登录方法
 */
public class SsoUserInfo {

    private String userid;
    private String username;
    private String nickname;
    private String password;
    private Map<String, Object> userMap;//存储其它信息
    public static SsoUserInfo build() {
        return new SsoUserInfo();

    }
    public String getUserid() {
        return userid;
    }

    public SsoUserInfo setUserid(String userid) {
        this.userid = userid;  return this;
    }

    public String getUsername() {
        return username;
    }

    public SsoUserInfo setUsername(String username) {
        this.username = username; return this;
    }

    public String getPassword() {
        return password;
    }

    public SsoUserInfo setPassword(String password) {
        this.password = password; return this;
    }

    public String getNickname() {
        return nickname;
    }

    public SsoUserInfo setNickname(String nickname) {
        this.nickname = nickname; return this;
    }

    public Map<String, Object> getUserMap() {
        return userMap;
    }

    public SsoUserInfo setUserMap(Map<String, Object> userMap) {
        this.userMap = userMap; return this;
    }
}
