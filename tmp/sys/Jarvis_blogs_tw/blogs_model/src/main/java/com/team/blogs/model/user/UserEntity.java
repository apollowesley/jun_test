package com.team.blogs.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: xiaokai
 * @Description: web用户实体
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "t_users", schema = "jarvis_blog", catalog = "")
@ApiModel("用户表")
public class UserEntity implements Serializable {

    @Id
    @Column
    @ApiModelProperty(value = "用户ID",example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "create_time")
    @ApiModelProperty("用户创建时间")
    private Timestamp createTime;

    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("用户名")
    private String userName;

    @Basic
    @Column(name = "user_password")
    @ApiModelProperty("密码")
    private String password;

    @Basic
    @Column(name = "login_count")
    @ApiModelProperty("登录次数")
    private Integer loginCount;

    @Basic
    @Column(name = "last_login_ip")
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    @Basic
    @Column(name = "last_login_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    @Basic
    @Column(name = "user_sex")
    @ApiModelProperty("性别 1、男 2、女")
    private Boolean sex;

    @Basic
    @Column(name = "avatar")
    @ApiModelProperty("用户头像")
    private String avatar;

    @Basic
    @Column(name = "email")
    @ApiModelProperty("邮箱")
    private String email;

    @Basic
    @Column(name = "mobile")
    @ApiModelProperty("手机号")
    private String mobile;

    @Basic
    @Column(name = "qq_number")
    @ApiModelProperty("QQ号")
    private String qqNumber;

    @Basic
    @Column(name = "we_chat")
    @ApiModelProperty("微信号")
    private String weChat;

    @Basic
    @Column(name = "occupation")
    @ApiModelProperty("职业")
    private String occupation;

    @Basic
    @Column(name = "summary")
    @ApiModelProperty("自我简介（150字）")
    private String summary;


}
