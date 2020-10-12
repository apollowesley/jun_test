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
 * 排班规则选项
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_rule_item")
@ApiModel(value="RuleItem对象", description="排班规则选项")
public class RuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "星期")
    private String week;

    @ApiModelProperty(value = "午别")
    private String noon;

    @ApiModelProperty(value = "排班规则ID")
    private Integer ruleId;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
