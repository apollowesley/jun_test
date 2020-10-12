package com.handy.activiti.service.impl;

import com.handy.activiti.service.IWorkFlowTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 10:01
 */
@Service
public class WorkFlowTaskServiceImpl implements IWorkFlowTaskService {
    @Autowired
    private TaskService taskService;

    /**
     * 通过流程id 查询任务
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public Task queryTaskByProcessId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 通过任务id，查询任务信息
     *
     * @param taskId
     * @return
     */
    @Override
    public Task queryTaskById(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 通过组查询任务
     *
     * @param groups 组
     * @param page   页数
     * @param limit  条数
     * @return
     */
    @Override
    public List<Task> queryTaskByGroup(List<String> groups, Integer page, Integer limit) {
        return taskService.createTaskQuery().taskCandidateGroupIn(groups).listPage(page, limit);
    }

    /**
     * 通过组查询任务数量
     *
     * @param groups
     * @return
     */
    @Override
    public Long queryTaskByGroupCount(List<String> groups) {
        return taskService.createTaskQuery().taskCandidateGroupIn(groups).count();
    }

    /**
     * 通过用户查询任务
     *
     * @param user  组
     * @param page  页数
     * @param limit 条数
     * @return
     */
    @Override
    public List<Task> queryTaskByUser(String user, Integer page, Integer limit) {
        return taskService.createTaskQuery().taskCandidateUser(user).orderByTaskId().desc().listPage((page - 1), limit);
    }

    /**
     * 通过用户查询任务数量
     *
     * @param user
     * @return
     */
    @Override
    public Long queryTaskByUserCount(String user) {
        return taskService.createTaskQuery().taskCandidateUser(user).count();
    }

    /**
     * 完成任务
     *
     * @param processId 流程id
     * @param paramsMap 任务携带变量
     */
    @Override
    public void complete(String processId, Map<String, Object> paramsMap) {
        Task task = this.queryTaskByProcessId(processId);
        Map<String, Object> filterMap = paramsMap.entrySet().stream().filter(map -> map.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 存储下个节点权限组，以及当前审核说明
        //设置任务完成时间
        taskService.setVariableLocal(task.getId(), "reason", paramsMap.get("reason"));
        taskService.setVariableLocal(task.getId(), "auditUserId", paramsMap.get("userId"));
        taskService.setVariableLocal(task.getId(), "createDate", new Date());
        taskService.complete(task.getId(), filterMap);
    }

    /**
     * 完成任务
     *
     * @param processId 流程id
     */
    @Override
    public void complete(String processId) {
        Task task = this.queryTaskByProcessId(processId);
        //设置任务完成时间
        taskService.setVariableLocal(task.getId(), "createDate", new Date());
        taskService.complete(task.getId());
    }
}
