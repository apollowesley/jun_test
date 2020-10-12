package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 检查申请
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_check_apply")
@ApiModel(value="CheckApply对象", description="检查申请")
public class CheckApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "病历ID")
    private Integer medicalId;

    @ApiModelProperty(value = "挂号ID")
    private Integer registId;

    @ApiModelProperty(value = "申请检测项目ID")
    private Integer applyId;

    @ApiModelProperty(value = "检测项目名称")
    private String itemName;

    @ApiModelProperty(value = "是否加急  1为加急 0为不加急")
    private Integer isUrgent;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "开立时间")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "开立医生ID")
    private Integer doctorId;

    @ApiModelProperty(value = "检查人员ID")
    private Integer checkOperId;

    @ApiModelProperty(value = "结果录入人员ID")
    private Integer resultOperId;

    @ApiModelProperty(value = "检查时间")
    private LocalDateTime checkTime;

    @ApiModelProperty(value = "检查结果")
    private String checkResult;

    @ApiModelProperty(value = "结果时间")
    private LocalDateTime resultTime;

    @ApiModelProperty(value = "执行状态")
    private Integer state;

    @ApiModelProperty(value = "记录类型 1-检查 2-检验 3-处置")
    private Integer recordType;

    @ApiModelProperty(value = "删除标记, 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
