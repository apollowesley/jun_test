package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsPortalUserRole)实体类
 *
 * @author makejava
 * @since 2020-03-25 23:31:16
 */
@Data
public class HorsPortalUserRole implements Serializable {
    private static final long serialVersionUID = 739114679004761481L;
    /**
    * ID
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}