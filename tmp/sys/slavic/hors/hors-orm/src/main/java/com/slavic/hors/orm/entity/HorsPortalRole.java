package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsPortalRole)实体类
 *
 * @author makejava
 * @since 2020-03-25 23:31:16
 */
@Data
public class HorsPortalRole implements Serializable {
    private static final long serialVersionUID = -61218369387885109L;
    /**
    * ID
    */
    private Long id;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色描述
    */
    private String roleDesc;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 数据版本号
    */
    private Integer horsVersion;

}