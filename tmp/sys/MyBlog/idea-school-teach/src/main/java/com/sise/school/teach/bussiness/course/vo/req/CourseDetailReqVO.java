package com.sise.school.teach.bussiness.course.vo.req;

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
public class CourseDetailReqVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 专栏id
     */
    private Integer specialId;

    /**
     * 课程题目
     */
    private String title;

    /**
     * 文章核心内容
     */
    private String content;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
