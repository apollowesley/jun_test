package com.sise.school.teach.bussiness.student.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author idea
 * @data 2018/12/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRespVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 学生代码
     */
    private String stuCode;

    /**
     * 收集代码
     */
    private String collectionCode;

    /**
     * 文章收藏名称
     */
    private String collectionName;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 描述
     */
    private String des;

    /**
     * 是否存在
     */
    private boolean existStatus;

    /**
     * 收集的种类（1 文章  2 视频）
     */
    private Integer collectionType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
