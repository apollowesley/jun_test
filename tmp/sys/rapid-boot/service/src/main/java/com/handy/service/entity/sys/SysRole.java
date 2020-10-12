package com.handy.service.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.handy.service.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 14:32
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity<SysRole> {
    @ApiModelProperty(value = "角色名")
    private String name;
    @ApiModelProperty(value = "排序号")
    private Long ordinal;
    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    private Boolean isChecked;
}
