package com.imooc.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 14:00
 */
@Data
public class Lesson {
    /** 主键 */
    private Long id;
    /** 课程标题 */
    private String title;
    /** 课程老师 */
    private String teacher;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
}
