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
 * 科室
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_office")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="Office对象", description="科室")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号",example = "0")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "科室编码")
    private String officeCode;

    @ApiModelProperty(value = "科室名称")
    private String officeName;

    @ApiModelProperty(value = "科室分类 外科、骨科等")
    private String officeTypeId;

    @ApiModelProperty(value = "科室类别 临床科室、医技科室等")
    private String officeCategoryId;

    @ApiModelProperty(value = "删除标记 0:保留,1:废除",example = "0")
    @TableField(fill = FieldFill.INSERT)
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
