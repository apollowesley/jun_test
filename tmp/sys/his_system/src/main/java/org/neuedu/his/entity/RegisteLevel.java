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
 * 挂号级别
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_registe_level")
@ApiModel(value="RegisteLevel对象", description="挂号级别")
public class RegisteLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "号别编码")
    private String registCode;

    @ApiModelProperty(value = "号别名称")
    private String registName;

    @ApiModelProperty(value = "挂号限额")
    private Integer registQuota;

    @ApiModelProperty(value = "显示顺序号")
    private Integer sequenceNo;

    @ApiModelProperty(value = "挂号费")
    private Double registFee;

    @ApiModelProperty(value = "结算类别 自费、医保、新农合等")
    private Integer payTypeId;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
