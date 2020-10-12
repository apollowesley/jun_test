package com.sise.school.teach.bussiness.student.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author idea
 * @data 2018/12/4
 */
@Data
@AllArgsConstructor
public class StudentCollectionReqVO {

    /**
     * id
     */
    private Integer id;
    /**
     * 学生代码
     */
    private String stuCode;

    /**
     * 收集代码
     */
    private String collectionCode;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
