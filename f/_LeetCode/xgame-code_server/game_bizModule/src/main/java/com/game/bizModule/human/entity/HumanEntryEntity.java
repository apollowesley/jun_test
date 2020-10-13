package com.game.bizModule.human.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色入口实体
 *
 * @author hjj2019
 * @since 2015/7/11
 *
 */
@Entity
@Table(name = "t_human")
public class HumanEntryEntity {
    /** 玩家角色 UId */
    @Id @Column(name = "human_uid", updatable = false)
    public Long _humanUId = null;
    /** 平台 UId 字符串 */
    @Column(name = "platform_uid_str", length = 64, nullable = false, updatable = false)
    public String _platformUIdStr = null;
    /** 角色全名 */
    @Column(name = "full_name", length = 48, nullable = false, updatable = false)
    public String _fullName = null;
    /** 角色名称 */
    @Column(name = "human_name", length = 32, nullable = false, updatable = false)
    public String _humanName = null;
    /** 服务器名称 */
    @Column(name = "server_name", length = 16, nullable = false, updatable = false)
    public String _serverName = null;
    /** 角色等级 */
    @Column(name = "human_level", nullable = false, updatable = false)
    public Integer _humanLevel = 0;
}
