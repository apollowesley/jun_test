package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsPortalLoginRecord)实体类
 *
 * @author makejava
 * @since 2020-04-02 20:57:33
 */
@Data
public class HorsPortalLoginRecord implements Serializable {
    private static final long serialVersionUID = 885565747027304353L;
    /**
    * 主键ID
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 登陆IP
    */
    private String loginIp;
    /**
    * IP地址
    */
    private String ipLocation;
    /**
    * 登陆时间
    */
    private Date createTime;
    /**
    * 终端系统名称
    */
    private String osName;
    /**
    * 终端版本号
    */
    private String osVersion;
    /**
    * 浏览器名称
    */
    private String browserName;
    /**
    * 浏览器版本号
    */
    private String browserVersion;

}