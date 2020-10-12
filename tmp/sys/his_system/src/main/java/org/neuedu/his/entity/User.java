package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号",example = "0")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String logName;

    @ApiModelProperty(value = "密码")
    private String logPwd;

    @ApiModelProperty(value = "真实姓名")
    private String relName;

    @ApiModelProperty(value = "用户所在科室",example = "0")
    private Integer byOfficeId;

    @ApiModelProperty(value = "用户类别 挂号收费员、门诊医生、医技医生等",example = "0")
    private Integer userTypeId;

    @ApiModelProperty(value = "职称信息 主任医师、副主任医师等",example = "0")
    private Integer academicInfoId;

    @ApiModelProperty(value = "是否参与排班 0:不参与,1:参与",example = "0")
    private Integer participateArrange;

    @ApiModelProperty(value = "用户状态 0:启用,1：禁用",example = "0")
    private Integer userStatus;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
