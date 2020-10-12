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
 * 草药模板
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_herbal_template")
@ApiModel(value="HerbalTemplate对象", description="草药模板")
public class HerbalTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "医生ID")
    private Integer userId;

    @ApiModelProperty(value = "处方类型 水煎煮/酒泡/打粉/制丸/装胶囊等")
    private String prescriptioType;

    @ApiModelProperty(value = "付数")
    private Integer payNumber;

    @ApiModelProperty(value = "频次")
    private String frequency;

    @ApiModelProperty(value = "用法")
    private String drugsUsage;

    @ApiModelProperty(value = "治法")
    private String therapy;

    @ApiModelProperty(value = "治法详细")
    private String detailed;

    @ApiModelProperty(value = "医嘱")
    private String advice;

    @ApiModelProperty(value = "使用范围 全院/科室/个人")
    private String scope;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
