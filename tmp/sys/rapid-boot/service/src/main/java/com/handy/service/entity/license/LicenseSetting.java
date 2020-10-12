package com.handy.service.entity.license;

import com.handy.service.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流程信息
 * </p>
 *
 * @author handy
 * @since 2019-09-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LicenseSetting extends BaseEntity<LicenseSetting> {

    /**
     * 工作流名称
     */
    private String name;

    /**
     * 工作流key
     */
    private String workFlowKey;

}
