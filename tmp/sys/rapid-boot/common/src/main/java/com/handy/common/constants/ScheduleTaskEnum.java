package com.handy.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author handy
 * @Description: {定时任务}
 * @date 2019/9/5 17:29
 */
@Getter
@AllArgsConstructor
public enum ScheduleTaskEnum {
    MSG_JOB(0L, "1号任务", "com.handy.task.MsgTask"),
    DEL_MSG_JOB(1L, "2号任务", "com.handy.task.MsgDelTask");

    private Long id;
    private String name;
    private String packageName;

    public static ScheduleTaskEnum getEnum(Long id) {
        for (ScheduleTaskEnum scheduleTaskEnum : ScheduleTaskEnum.values()) {
            if (scheduleTaskEnum.getId().equals(id)) {
                return scheduleTaskEnum;
            }
        }
        return null;
    }

}
