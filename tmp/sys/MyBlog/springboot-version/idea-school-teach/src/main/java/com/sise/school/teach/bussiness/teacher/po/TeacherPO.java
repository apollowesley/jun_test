package com.sise.school.teach.bussiness.teacher.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPO {

    /**
     * id
     */
    private Integer id;

    /**
     * 教师名称
     */
    private String name;

    /**
     * 教师系别
     */
    private Integer type;

    /**
     * 教师代码
     */
    private String code;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 教师头像
     */
    private String picture;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
