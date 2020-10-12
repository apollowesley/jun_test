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
 * 门诊病历首页 病历卡
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_medical_record")
@ApiModel(value="MedicalRecord对象", description="门诊病历首页 病历卡")
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "病历号")
    private String caseNumber;

    @ApiModelProperty(value = "挂号ID")
    private Integer registId;

    @ApiModelProperty(value = "主诉")
    private String readme;

    @ApiModelProperty(value = "现病史")
    private String present;

    @ApiModelProperty(value = "现病治疗情况")
    private String presentTreat;

    @ApiModelProperty(value = "既往史")
    private String history;

    @ApiModelProperty(value = "过敏史")
    private String allergy;

    @ApiModelProperty(value = "体格检查")
    private String physique;

    @ApiModelProperty(value = "检查建议")
    private String proposal;

    @ApiModelProperty(value = "注意事项")
    private String careful;

    @ApiModelProperty(value = "检查结果")
    private String checkResult;

    @ApiModelProperty(value = "诊断结果")
    private String diagnosis;

    @ApiModelProperty(value = "处理意见")
    private String handling;

    @ApiModelProperty(value = "病历状态 1-暂存 2-已提交 3-诊毕'")
    private Integer caseState;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
