package com.game.bizModule.player.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体
 * 
 * @author hjj2019
 * @since 2014/9/15
 * 
 */
@Entity
@Table(name = "t_player")
public class PlayerEntity {
    /** 平台 UId 字符串 */
    @Id @Column(name = "platform_uid_str", length = 64, updatable = false)
    public String _platformUIdStr = null;
    /** 用户名 */
    @Column(name = "user_name", length = 32, updatable = false)
    public String _userName = null;
    /** 密码 */
    @Column(name = "user_pass", length = 128)
    public String _userPass = null;
    /** Pf 值 */
    @Column(name = "pf", length = 32, updatable = false)
    public String _pf = null;
    /** 创建时间 */
    @Column(name = "create_time", updatable = false)
    public Long _createTime = null;
    /** passbook 创建时间 */
    @Column(name = "passbook_create_time", updatable = false)
    public Long _passbookCreateTime = null;
    /** 最后登陆时间 */
    @Column(name = "last_login_time")
    public Long _lastLoginTime = null;
    /** 最后登陆 IP 地址 */
    @Column(name = "last_login_ip_addr", length = 64)
    public String _lastLoginIpAddr = null;
}
