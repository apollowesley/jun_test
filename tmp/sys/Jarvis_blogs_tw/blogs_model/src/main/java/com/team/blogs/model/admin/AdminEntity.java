package com.team.blogs.model.admin;

import com.team.blogs.model.role.RoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_admins", schema = "jarvis_blog", catalog = "")
@ApiModel("管理员表")
public class AdminEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("用户名")
    private String userName;


    @Basic
    @Column(name = "password")
    @ApiModelProperty("密码")
    private String passWord;


    @Basic
    @Column(name = "nick_name")
    @ApiModelProperty("昵称")
    private String nickName;


    @Basic
    @Column(name = "gender")
    @ApiModelProperty("性别(1:男2:女)")
    private Boolean gender;


    @Basic
    @Column(name = "avatar")
    @ApiModelProperty("个人头像")
    private String avatar;


    @Basic
    @Column(name = "email")
    @ApiModelProperty("邮箱")
    private String email;


    @Basic
    @Column(name = "birthday")
    @ApiModelProperty("出生年月日")
    private LocalDateTime birthday;

    @Basic
    @Column(name = "mobile")
    @ApiModelProperty("手机")
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
    @ApiModelProperty("自我简介最多150字")
    private String summary;


    @Basic
    @Column(name = "login_count")
    @ApiModelProperty("登录次数")
    private Integer loginCount;


    @Basic
    @Column(name = "last_login_time")
    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;


    @Basic
    @Column(name = "last_login_ip")
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;


    /**
     * 以下字段不存入数据库
     */
    @Transient
    private List<String> photoList; //用户头像

    /**
     * 所拥有的角色名
     */
    @Transient
    private List<String> roleNames;

    /**
     * 所拥有的角色
     */
    @Transient
    private List<RoleEntity> roleList;

    /**
     * 验证码
     */
    @Transient
    private String validCode;



}
