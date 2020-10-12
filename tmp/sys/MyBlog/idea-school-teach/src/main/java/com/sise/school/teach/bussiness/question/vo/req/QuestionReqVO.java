package com.sise.school.teach.bussiness.question.vo.req;

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
public class QuestionReqVO {

    private Integer id;

    private String no;

    private String questionContent;

    private Integer courseId;

    private Integer courseDetailId;

    private Integer goodNums;

    private Date createTime;

    private Date updateTime;
}
