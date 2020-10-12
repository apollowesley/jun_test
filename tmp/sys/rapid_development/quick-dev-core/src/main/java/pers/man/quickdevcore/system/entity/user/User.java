package pers.man.quickdevcore.system.entity.user;

import pers.man.quickdevcore.system.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:05
 * @project quick-dev
 */
public class User extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像地址
     */
    private String profilePictureUrl;
    /**
     * 电话
     */
    private String phone;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 用户状态
     */
    private String userState;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 上次登录ip
     */
    private String lastLoginIp;
    /**
     * 联系地址
     */
    private String address;
}
