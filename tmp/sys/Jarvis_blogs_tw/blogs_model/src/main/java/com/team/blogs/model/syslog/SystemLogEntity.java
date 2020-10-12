package com.team.blogs.model.syslog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_system_log", schema = "jarvis_blog", catalog = "")
@ApiModel("操作日志记录表")
public class SystemLogEntity {

    private static final long serialVersionUID = -4851055162892178225L;


    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("操作用户名")
    private String userName;

    @Basic
    @Column(name = "admin_uid")
    @ApiModelProperty("操作人uid")
    private Long adminUid;

    @Basic
    @Column(name = "ip")
    @ApiModelProperty("请求IP")
    private String ip;

    @Basic
    @Column(name = "url")
    @ApiModelProperty("请求地址")
    private String url;

    @Basic
    @Column(name = "type")
    @ApiModelProperty("请求方式 GET POST")
    private String type;

    @Basic
    @Column(name = "class_path")
    @ApiModelProperty("请求类路径")
    private String classPath;

    @Basic
    @Column(name = "method")
    @ApiModelProperty("方法名")
    private String method;

    @Basic
    @Column(name = "params")
    @ApiModelProperty("参数")
    private String params;

    @Basic
    @Column(name = "operation")
    @ApiModelProperty("描述")
    private String operation;

}
