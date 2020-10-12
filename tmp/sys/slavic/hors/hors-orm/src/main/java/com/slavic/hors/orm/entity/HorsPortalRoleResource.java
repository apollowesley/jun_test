package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsPortalRoleResource)实体类
 *
 * @author makejava
 * @since 2020-03-25 23:31:16
 */
@Data
public class HorsPortalRoleResource implements Serializable {
    private static final long serialVersionUID = -75397887111940391L;
    /**
    * ID
    */
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 资源ID
    */
    private Long resourceId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}