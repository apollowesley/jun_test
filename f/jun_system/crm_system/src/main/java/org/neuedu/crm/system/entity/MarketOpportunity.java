package org.neuedu.crm.system.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.neuedu.crm.system.util.ConstUtil;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 营销机会
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MarketOpportunity对象", description="营销机会")
public class MarketOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    @TableId(type = IdType.INPUT)
    private Integer id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "机会来源")
    private String opportunitySource;

    @ApiModelProperty(value = "成功机率")
    private Integer probabilitySuccess;

    @ApiModelProperty(value = "概要")
    private String summary;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "联系电话")
    private String linkPhone;

    @ApiModelProperty(value = "机会描述")
    private String opportunitDescribe;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建时间")
    //@JsonFormat(pattern = ConstUtil.DEFAULT_DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = ConstUtil.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "指派人")
    private Integer appointId;

    @ApiModelProperty(value = "指派时间")
    //@JsonFormat(pattern = ConstUtil.DEFAULT_DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = ConstUtil.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime appointTime;

    @ApiModelProperty(value = "状态 0未指派,1已指派,2 已制定计划项,3 执行中,4开发成功,5开发失败")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
