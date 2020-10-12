package com.handy.param.workflow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 12:59
 */
@Data
public class TaskParam implements Serializable {
    @ApiModelProperty("任务ID")
    private String id;
    @ApiModelProperty("任务名称")
    private String name;
    @ApiModelProperty("任务描述")
    private String description;

}
