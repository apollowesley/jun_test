package com.sise.school.teach.bussiness.course.vo.resp;

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
public class SpecialRespVO {

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
     * 状态
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
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
