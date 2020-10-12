package com.handy.activiti.service;

import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 10:00
 */
public interface IWorkFlowTaskService {

    /**
     * 通过流程id 查询任务
     *
     * @param processInstanceId 流程id
     * @return
     */
    Task queryTaskByProcessId(String processInstanceId);

    /**
     * 通过任务id，查询任务信息
     *
     * @param taskId 任务id
     * @return
     */
    Task queryTaskById(String taskId);

    /**
     * 通过组查询任务
     *
     * @param groups 组
     * @param page   页数
     * @param limit  条数
     * @return
     */
    List<Task> queryTaskByGroup(List<String> groups, Integer page, Integer limit);

    /**
     * 通过组查询任务数量
     *
     * @param groups
     * @return
     */
    Long queryTaskByGroupCount(List<String> groups);

    /**
     * 通过用户查询任务
     *
     * @param user  用户
     * @param page  页数
     * @param limit 条数
     * @return
     */
    List<Task> queryTaskByUser(String user, Integer page, Integer limit);

    /**
     * 通过用户查询任务数量
     *
     * @param user
     * @return
     */
    Long queryTaskByUserCount(String user);

    /**
     * 完成任务
     *
     * @param processId    流程id
     * @param paramsMap 任务携带变量
     */
    void complete(String processId, Map<String, Object> paramsMap);

    /**
     * 完成任务
     *
     * @param processId 流程id
     */
    void complete(String processId);
}
