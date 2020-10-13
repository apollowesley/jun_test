package com.baomidou.crab.v1.entity;

import com.baomidou.crab.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 评
     */
    private Long discuss;

    /**
     * 赞
     */
    private Long praise;


}
