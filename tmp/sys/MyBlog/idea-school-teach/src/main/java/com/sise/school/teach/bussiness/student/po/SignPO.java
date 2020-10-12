package com.sise.school.teach.bussiness.student.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程报名表
 * @author idea
 * @data 2018/10/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignPO {

    /**
     * id
     */
    private Integer id;

    /**
     * 学号
     */
    private String stuNo;

    /**
     * 课程编号
     */
    private Integer courseNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
