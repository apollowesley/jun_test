package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 成药详情
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_prescription_detailed")
@ApiModel(value="PrescriptionDetailed对象", description="成药详情")
public class PrescriptionDetailed implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "成药处方/模板ID")
    private Integer prescriptionId;

    @ApiModelProperty(value = "药品ID")
    private Integer drugsId;

    @ApiModelProperty(value = "用法")
    @TableField("drugs_Usage")
    private String drugsUsage;

    @ApiModelProperty(value = "用量")
    private String dosage;

    @ApiModelProperty(value = "频次")
    private String frequency;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
