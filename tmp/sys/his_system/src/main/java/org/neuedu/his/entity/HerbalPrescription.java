package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 草药处方
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_herbal_prescription")
@ApiModel(value="HerbalPrescription对象", description="草药处方")
public class HerbalPrescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "病历ID")
    private Integer medicalId;

    @ApiModelProperty(value = "挂号ID")
    private Integer registId;

    @ApiModelProperty(value = "开立医生ID")
    private Integer userId;

    @ApiModelProperty(value = "处方名称")
    private String prescriptionName;

    @ApiModelProperty(value = "开立时间")
    private LocalDateTime prescriptionTime;

    @ApiModelProperty(value = "处方类型 水煎煮/酒泡/打粉/制丸/装胶囊等")
    private String prescriptioType;

    @ApiModelProperty(value = "付数")
    private BigDecimal payNumber;

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

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
