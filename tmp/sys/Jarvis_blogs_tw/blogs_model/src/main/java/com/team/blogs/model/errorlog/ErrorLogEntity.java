package com.team.blogs.model.errorlog;

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
@Table(name = "t_error_log", schema = "jarvis_blog", catalog = "")
@ApiModel("操作日志异常记录表")
public class ErrorLogEntity {

    private static final long serialVersionUID = -4851055162892178225L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "exception_json")
    @ApiModelProperty("异常对象json格式")
    private String exceptionJson;

    @Basic
    @Column(name = "exception_message")
    @ApiModelProperty("异常简单信息,等同于e.getMessage")
    private String exceptionMessage;


}
