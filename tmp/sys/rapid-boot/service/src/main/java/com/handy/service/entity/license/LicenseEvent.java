package com.handy.service.entity.license;

import com.handy.service.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author handy
 * @since 2019-09-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LicenseEvent extends BaseEntity<LicenseEvent> {

    /**
     * 事项
     */
    private Long licenseId;

    /**
     * 事项名称
     */
    private String licenseName;

    /**
     * 申请人
     */
    private Long accountId;

    /**
     * 申请人名称
     */
    private String accountName;

    /**
     * 申请时间
     */
    private Date startTime;

    /**
     * 审批人
     */
    private Long approveId;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 状态
     */
    private Long statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 表单id
     */
    private Long formId;

    /**
     * 流程id
     */
    private String workFlowId;

}
