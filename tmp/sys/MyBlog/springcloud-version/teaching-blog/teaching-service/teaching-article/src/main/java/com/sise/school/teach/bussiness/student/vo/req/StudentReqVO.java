package com.sise.school.teach.bussiness.student.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author idea
 * @data 2018/10/3
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentReqVO {

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

}
