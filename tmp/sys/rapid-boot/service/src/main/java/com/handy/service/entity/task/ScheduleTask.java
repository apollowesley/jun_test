package com.handy.service.entity.task;

import com.handy.service.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author handy
 * @since 2019-09-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ScheduleTask extends BaseEntity<ScheduleTask> {
    @ApiModelProperty(value = "描述任务")
    private String jobName;
    @ApiModelProperty(value = "任务表达式")
    private String cron;
    @ApiModelProperty(value = "状态:0未启动false/1启动true")
    private Boolean status;
    @ApiModelProperty(value = "任务执行方法id")
    private Long clazzPathId;
    @ApiModelProperty(value = "任务执行方法name")
    private String clazzPathName;
    @ApiModelProperty(value = "描述")
    private String note;
}
