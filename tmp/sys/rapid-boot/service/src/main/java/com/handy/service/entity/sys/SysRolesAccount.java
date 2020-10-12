package com.handy.service.entity.sys;

import com.handy.service.entity.BaseEntity;
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
public class SysRolesAccount extends BaseEntity<SysRolesAccount> {
    private Long accountId;
    private Long roleId;
    private String note;
}
