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
 * 发票
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_invoice")
@ApiModel(value="Invoice对象", description="发票")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "发票号码")
    private String invoiceNum;

    @ApiModelProperty(value = "发票金额")
    private Double money;

    @ApiModelProperty(value = "发票状态 1-正常  2-作废")
    private BigDecimal state;

    @ApiModelProperty(value = "收/退费时间")
    private LocalDateTime creationTime;

    @ApiModelProperty(value = "收/退费人员ID")
    private Integer userId;

    @ApiModelProperty(value = "挂号ID")
    private Integer registId;

    @ApiModelProperty(value = "收费方式")
    private Integer feeTypeId;

    @ApiModelProperty(value = "冲红发票号码")
    private String back;

    @ApiModelProperty(value = "发票状态  0-未日结  1-已提交  2-已审核")
    private Integer invoiceState;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
