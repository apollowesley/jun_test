package com.baomidou.crab.v1.entity;

import com.baomidou.crab.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 友情链接表
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Link extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 摘要
     */
    private String summary;


}
