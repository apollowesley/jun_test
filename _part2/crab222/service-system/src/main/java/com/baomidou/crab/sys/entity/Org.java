package com.baomidou.crab.sys.entity;

import com.baomidou.crab.common.utils.Pinyin4jUtils;
import com.baomidou.crab.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统机构表
 * </p>
 *
 * @author jobob
 * @since 2018-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_org")
public class Org extends BaseEntity {

    @ApiModelProperty(value = "父 ID")
    private Long pid;

    @ApiModelProperty(value = "企业 ID")
    private Long companyId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "首字母")
    private String initial;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;


    public Org initialName() {
        if (null != name) {
            this.initial = Pinyin4jUtils.converterToAllFirstSpell(name);
        }
        return this;
    }
}
