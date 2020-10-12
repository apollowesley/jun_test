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
 * 权限表
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_acl")
public class Acl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 权限码
     */
    @TableField("code")
    private String code;

    /**
     * 权限模块编号
     */
    @TableField("aclModuleId")
    private Integer aclModuleId;

    /**
     * 地址
     */
    @TableField("url")
    private String url;

    /**
     * 权限类型,菜单,按钮,其他
     */
    @TableField("type")
    private String type;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序号
     */
    @TableField("seq")
    private Integer seq;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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
