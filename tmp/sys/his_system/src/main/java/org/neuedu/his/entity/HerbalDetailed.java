package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 草药详情
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_herbal_detailed")
@ApiModel(value="HerbalDetailed对象", description="草药详情")
public class HerbalDetailed implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "草药处方/模板ID")
    private Integer herbalTempId;

    @ApiModelProperty(value = "药品ID")
    private Integer herbalId;

    @ApiModelProperty(value = "用量")
    private String dosage;

    @ApiModelProperty(value = "药品单位")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
