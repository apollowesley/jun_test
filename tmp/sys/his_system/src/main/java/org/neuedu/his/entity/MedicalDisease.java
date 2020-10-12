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
 * 疾病诊治
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_medical_disease")
@ApiModel(value="MedicalDisease对象", description="疾病诊治")
public class MedicalDisease implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "病历ID")
    private Integer medicalId;

    @ApiModelProperty(value = "挂号ID")
    private Integer registId;

    @ApiModelProperty(value = "疾病ID")
    private Integer diseaseId;

    @ApiModelProperty(value = "诊断类型 1-西医 2-中医")
    private Integer diagnoseType;

    @ApiModelProperty(value = "发病日期")
    private LocalDateTime getSiskDate;

    @ApiModelProperty(value = "诊断种类 1-初诊 2-终诊")
    private Integer diagnoseCate;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
