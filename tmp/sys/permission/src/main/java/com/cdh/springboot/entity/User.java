package com.cdh.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 真实名
     */
    @TableField("rel_name")
    private String relName;

    /**
     * 登录名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 联系方式
     */
    @TableField("tel")
    private String tel;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("pwd")
    private String pwd;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 部门编号
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 操作人
     */
    @TableField("operator")
    private String operator;

    /**
     * 操作时间
     */
    @TableField("operator_time")
    private LocalDateTime operatorTime;

    /**
     * 操作ip
     */
    @TableField("operator_ip")
    private String operatorIp;


}
