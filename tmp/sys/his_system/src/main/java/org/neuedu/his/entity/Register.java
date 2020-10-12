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
 * 现场挂号
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_register")
@ApiModel(value="Register对象", description="现场挂号")
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "病历号 自动生成，唯一、必填")
    private String caseNum;

    @ApiModelProperty(value = "姓名 必填")
    private String relName;

    @ApiModelProperty(value = "性别 必填")
    private Integer gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "出生日期 出生日期填入，年龄(周岁)自动计算")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "结算类别 必填")
    private Integer payTypeId;

    @ApiModelProperty(value = "挂号级别 必填")
    private Integer registLevelId;

    @ApiModelProperty(value = "挂号科室 必填")
    private Integer officeId;

    @ApiModelProperty(value = "看诊医生 必填")
    private Integer userId;

    @ApiModelProperty(value = "看诊时间")
    private LocalDateTime visitDate;

    @ApiModelProperty(value = "午别 上午，下午")
    private String noon;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "家庭住址")
    private String homeAddress;

    @ApiModelProperty(value = "是否要病历本 如要单独收费 1 元")
    private Integer isbook;

    @ApiModelProperty(value = "应收金额 由系统根据挂号的级别及看诊医生,是否要病历本,自动算出")
    private Double amountMoney;

    @ApiModelProperty(value = "患者状态 已挂号，未看诊，已收费，药品已发放，已检查....)")
    private Integer visitState;

    @ApiModelProperty(value = "挂号员ID")
    private String registId;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
