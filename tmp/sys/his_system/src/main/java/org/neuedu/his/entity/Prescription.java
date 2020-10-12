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
 * 处方
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_prescription")
@ApiModel(value="Prescription对象", description="处方")
public class Prescription implements Serializable {

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

    @ApiModelProperty(value = "处方状态")
    private Integer prescriptionState;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
