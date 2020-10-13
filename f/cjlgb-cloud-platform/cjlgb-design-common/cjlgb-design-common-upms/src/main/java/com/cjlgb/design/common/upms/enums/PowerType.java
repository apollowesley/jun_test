package com.cjlgb.design.common.upms.enums;

import com.cjlgb.design.common.mybatis.service.NumberEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WFT
 * @date 2020/7/13
 * description: 权限类型
 */
@Getter
@AllArgsConstructor
public enum PowerType implements NumberEnum {

    /**
     * 页面
     */
    page(1),

    /**
     * 按钮
     */
    button(2)
    ;
    private final int value;
}
