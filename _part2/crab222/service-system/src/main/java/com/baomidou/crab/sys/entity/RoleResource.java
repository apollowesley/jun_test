package com.baomidou.crab.sys.entity;

import com.baomidou.crab.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色资源关联表
 * </p>
 *
 * @author jobob
 * @since 2018-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_resource")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色 ID")
    private Long roleId;

    @ApiModelProperty(value = "资源 ID")
    private Long resourceId;

}
