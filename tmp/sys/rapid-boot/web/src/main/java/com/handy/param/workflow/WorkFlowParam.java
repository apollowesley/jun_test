package com.handy.param.workflow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 11:58
 */
@Data
public class WorkFlowParam implements Serializable {
    @ApiModelProperty(value = "流程ID")
    private String id;
    @ApiModelProperty(value = "类型")
    private String name;
    @ApiModelProperty(value = "流程key")
    private String key;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "版本")
    private Integer version;
    @ApiModelProperty(value = "部署时间")
    private Date deploymentTime;
}
