package com.sise.school.teach.bussiness.course.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRespVO {

    /**
     * id
     */
    private int id;

    /**
     * 课程名称
     */
    private String title;

    /**
     * 课程类型
     */
    private String type;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 是否上架 （1,审核中;2,未上线;3，已上线;4，已下架）
     */
    private Integer status;

    /**
     * 课程主讲人
     */
    private String author;

    /**
     * 专题id
     */
    private Integer specialId;

    /**
     * 喜欢人数
     */
    private Integer loveNums;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 更新日期
     */
    private Date updateTime;

}
