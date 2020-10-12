package com.handy.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author handy
 * @Description: {工作流状态}
 * @date 2019/9/12 14:57
 */
@Getter
@AllArgsConstructor
public enum WorkFlowStatusEnum {
    APPLY(1L, "待审批"),
    REJECT(2L, "审批驳回"),
    PASS(3L, "审批通过");

    private Long id;
    private String name;

}
