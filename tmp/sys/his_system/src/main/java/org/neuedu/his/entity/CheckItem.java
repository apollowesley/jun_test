package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 检查项目
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_check_item")
@ApiModel(value="CheckItem对象", description="检查项目")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "项目编码")
    private Integer itemCode;

    @ApiModelProperty(value = "项目类型")
    private Integer itemType;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "拼音助记码")
    private String mnemonicCode;

    @ApiModelProperty(value = "规格")
    private String format;

    @ApiModelProperty(value = "项目单价")
    private String price;

    @ApiModelProperty(value = "执行科室ID")
    private Integer officeId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;


}
