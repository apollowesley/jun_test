package com.sise.school.teach.bussiness.student.vo.resp;

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
public class StudentRespVO {

    /*
     * 学生姓名
     */
    private String account;

    /*
     * 手机号码
     */
    private String tel;

    /*
     * 电子邮件
     */
    private String email;
}
