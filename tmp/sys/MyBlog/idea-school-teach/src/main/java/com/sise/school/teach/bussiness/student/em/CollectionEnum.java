package com.sise.school.teach.bussiness.student.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author idea
 * @data 2018/12/5
 */
@Getter
@AllArgsConstructor
public enum CollectionEnum{

    ARTICLE_TYPE("文章类型", 1),

    VIDEO_TYPE("视频类型",2);

    /**
     * 描述
     */
    private String des;

    /**
     * 代码
     */
    private Integer code;



}
