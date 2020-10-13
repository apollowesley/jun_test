package com.cjlgb.design.common.upms.entity;

import com.cjlgb.design.common.core.bean.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author WFT
 * @date 2020/7/14
 * description:员工角色
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRole extends BaseEntity {

    /**
     * 员工Id
     */
    private Long employId;

    /**
     * 角色Id
     */
    private Long roleId;

}
