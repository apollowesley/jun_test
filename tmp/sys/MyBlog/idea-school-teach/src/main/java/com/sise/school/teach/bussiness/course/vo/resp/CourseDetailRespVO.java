package com.sise.school.teach.bussiness.course.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author idea
 * @data 2018/10/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailRespVO {

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

    /**
     * 课程名称集合
     */
    private List<CourseRespVO> courseRespVOS;
}
