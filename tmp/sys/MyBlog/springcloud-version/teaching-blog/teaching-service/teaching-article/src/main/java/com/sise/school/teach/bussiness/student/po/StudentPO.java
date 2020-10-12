package com.sise.school.teach.bussiness.student.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author idea
 * @data 2018/10/3
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentPO implements Serializable {

    /*
     * 学号
     */
    private Integer id;

    /*
     * 密码
     */
    private String password;

    /*
     * 学生姓名
     */
    private String account;

    /**
     * 学生代码
     */
    private String stuCode;

    /*
     * 手机号码
     */
    private String tel;

    /*
     * 电子邮件
     */
    private String email;

    /*
     * 系别类型
     */
    private Integer type;

    /*
     * 密码盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
