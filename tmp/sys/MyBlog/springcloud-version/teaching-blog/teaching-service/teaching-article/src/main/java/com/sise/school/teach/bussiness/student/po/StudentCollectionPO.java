package com.sise.school.teach.bussiness.student.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/12/4
 */
@Data
@NoArgsConstructor
public class StudentCollectionPO {

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
