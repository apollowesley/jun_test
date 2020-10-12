package com.sise.school.teach.bussiness.course.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程专栏
 * @author idea
 * @data 2018/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialPO {

    /**
     * id
     */
    private int id;

    /**
     * 专栏名称
     */
    private String specialName;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 状态（1,审核中;2,未上线;3，已上线;4，已下架）
     */
    private Integer status;

    /**
     * 图片路径
     */
    private String imageUrl;

    /**
     * 专栏描述
     */
    private String des;

    /**
     * 课程观看次数
     */
    private Integer visitCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
