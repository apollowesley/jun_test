package com.handy.service.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author handy
 * @Description: {字段父类}
 * @date 2019/8/22 14:32
 */
@Data
public class BaseEntity<T> implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "修改人")
    private String mender;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

}
