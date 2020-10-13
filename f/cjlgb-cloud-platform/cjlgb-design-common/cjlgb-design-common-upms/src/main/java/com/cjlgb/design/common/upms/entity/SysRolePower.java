package com.cjlgb.design.common.upms.entity;

import com.cjlgb.design.common.core.bean.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePower extends BaseEntity {

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 权限Id
     */
    private Long powerId;

    /**
     * 权限唯一标识
     */
    private String permission;

}
