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
 * 角色用户关系表
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_user")
public class RoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色编号
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

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
