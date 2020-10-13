package com.baomidou.crab.v1.entity;

import com.baomidou.crab.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tag extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;


}
