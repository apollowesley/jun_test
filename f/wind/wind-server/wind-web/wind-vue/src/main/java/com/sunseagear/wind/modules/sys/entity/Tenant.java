package com.sunseagear.wind.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sunseagear.common.mvc.entity.DataEntity;
import lombok.Data;

/**
 * All rights Reserved, Designed By www.sunseagear.com
 *
 * @version V1.0
 * @package sys
 * @title: 租户管理控制器
 * @description: 租户管理控制器
 * @author: 未知
 * @date: 2019-11-28 06:24:52
 * @copyright: www.sunseagear.com Inc. All rights reserved.
 */

@Data
@TableName("sys_tenant")
@SuppressWarnings("serial")
public class Tenant extends DataEntity<String> {


    @TableId(value = "id", type = IdType.UUID)
    private String id; //id
    @TableField(value = "contact")
    private String contact;  //联系人
    @TableField(value = "phone")
    private String phone;  //电话
    @TableField(value = "name")
    private String name;  //租户名称
    @TableField(value = "tenant_id")
    private String tenantId;
    @TableField(value = "user_id")
    private String userId;
    @TableField(exist = false)
    private String userName;
}
