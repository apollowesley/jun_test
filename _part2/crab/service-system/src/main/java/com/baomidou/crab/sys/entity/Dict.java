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
 * 系统字典表
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict extends BaseEntity {

    @ApiModelProperty(value = "父 ID")
    private Long pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "首字母")
    private String initial;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "系统字典 0、否 1、是")
    private Integer sys;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态 -1、禁用 0、正常")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;


    public Dict initialName() {
        if (null != name) {
            this.initial = Pinyin4jUtils.converterToAllFirstSpell(name);
        }
        return this;
    }
}
