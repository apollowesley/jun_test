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
 * 常数类别 常数类别
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_constant_category")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="ConstantCategory对象", description="常数类别 常数类别")
public class ConstantCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号",example = "0")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "常数项编码")
    private String constantCode;

    @ApiModelProperty(value = "常数项名称")
    private String constantName;

    @ApiModelProperty(value = "常数项类别")
    private String constantType;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "删除标记 0:保留(默认),1:废除",example = "0")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
