package org.iartisan.admin.template.service.bpm.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * task entity
 *
 * @author King
 * @since 2018/7/12
 */
@Data
public class TaskEntity {

    /**
     * 流程ID
     */
    private String instId;

    /**
     * 流程实例名称
     */
    private String instName;

    /**
     * 当前任务节点名称
     */
    private String taskNodeName;


    private Date createTime;
}
