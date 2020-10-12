package com.sise.school.teach.bussiness.question.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPO {

    private Integer id;

    private String no;

    private String questionContent;

    private Integer courseId;

    private Integer courseDetailId;

    private Integer goodNums;

    private Date createTime;

    private Date updateTime;
}
