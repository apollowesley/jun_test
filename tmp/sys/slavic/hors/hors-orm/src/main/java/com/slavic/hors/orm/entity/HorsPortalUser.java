package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsPortalUser)实体类
 *
 * @author makejava
 * @since 2020-03-25 23:31:16
 */
@Data
public class HorsPortalUser implements Serializable {
    private static final long serialVersionUID = -43170728913302743L;
    /**
    * ID
    */
    private Long id;
    /**
    * 登录账号
    */
    private String username;
    /**
    * 登录密码
    */
    private String password;
    /**
    * 真实姓名
    */
    private String realName;
    /**
    * 用户昵称
    */
    private String nickName;
    /**
    * 用户手机号
    */
    private String userPhone;
    /**
    * 头像地址
    */
    private Long avatarUrl;
    /**
    * 用户邮箱
    */
    private String userEmail;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 数据状态: -1停用 1正常
    */
    private Integer userStatus;
    /**
    * 数据版本号
    */
    private Integer horsVersion;

}