package com.team.blogs.model.guest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: xiaokai
 * @Description: 游客表
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "t_guests", schema = "jarvis_blog", catalog = "")
@ApiModel("游客表")
public class GuestEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("用户名")
    private String user_name;

    @Basic
    @Column(name = "email")
    @ApiModelProperty("邮箱")
    private String email;

    @Basic
    @Column(name = "login_count")
    @ApiModelProperty("登录次数")
    private Integer login_count;

    @Basic
    @Column(name = "last_login_time")
    @ApiModelProperty("最后登录时间")
    private Date last_login_time;

    @Basic
    @Column(name = "last_login_ip")
    @ApiModelProperty("最后登录ip")
    private String last_login_ip;


}
