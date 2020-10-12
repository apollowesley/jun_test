package com.sise.school.teach.bussiness.student.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/3
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignReqVO {

    /**
     * id
     */
    private String id;

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
