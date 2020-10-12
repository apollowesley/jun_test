package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

/**
 * (HorsPortalResource)实体类
 *
 * @author makejava
 * @since 2020-03-25 23:31:16
 */
@Data
public class HorsPortalResource implements Serializable {
    private static final long serialVersionUID = 484324951052919407L;
    /**
    * ID
    */
    private Long id;
    /**
    * 资源类型:RESOURCE_MENU,RESOURCE_BUTTON,RESOURCE_INTERFACE
    */
    private String resourceType;
    /**
    * 资源名称
    */
    private String resourceName;
    /**
    * 资源描述
    */
    private String resourceDesc;
    /**
    * 父类ID
    */
    private Long parentId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

    private List<HorsPortalResource> children;

    private boolean hasChildren = !CollectionUtils.isEmpty(children);
}