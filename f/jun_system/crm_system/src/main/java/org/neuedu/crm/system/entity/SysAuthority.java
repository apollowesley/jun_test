package org.neuedu.crm.system.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysAuthority对象", description="权限")
public class SysAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Integer id;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限地址")
    private String authorityUrl;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "上级模块")
    private Integer parentId;

    @ApiModelProperty(value = "排序号")
    private Integer sortNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
