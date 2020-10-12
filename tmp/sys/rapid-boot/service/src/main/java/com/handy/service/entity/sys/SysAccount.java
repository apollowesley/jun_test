package com.handy.service.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.handy.service.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 14:32
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysAccount extends BaseEntity<SysAccount> {
    @ApiModelProperty(value = "帐号")
    private String code;
    @ApiModelProperty(value = "昵称")
    private String name;
    @ApiModelProperty(value = "性别")
    private Boolean sex;
    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthData;
    @ApiModelProperty(value = "Logo")
    private String logo;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "是否锁定")
    private Boolean isLocked;
    @ApiModelProperty(value = "登录次数")
    private Long loginCount;
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    @ApiModelProperty(value = "简介")
    private String note;

    @ApiModelProperty(value = "角色集合,逗号分隔")
    @TableField(exist = false)
    private String roles;
}
