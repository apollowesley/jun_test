package org.iartisan.admin.template.service.bpm;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.iartisan.admin.template.service.bpm.entity.TaskEntity;
import org.iartisan.admin.template.service.entity.MessageEntity;
import org.iartisan.admin.template.service.entity.StaffEntity;
import org.iartisan.admin.template.service.management.SystemMsgManagementService;
import org.iartisan.admin.template.service.query.StaffQueryService;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.exception.NoRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * workflow management
 *
 * @author King
 * @since 2018/7/11
 */
@Slf4j
@Service
public class BpmWorkflowManagement {

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected FormService formService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private StaffQueryService staffQueryService;

    @Autowired
    private SystemMsgManagementService msgManagementService;


    /**
     * 启动流程
     *
     * @param processId
     * @param staffId
     * @param variables
     */
    public void startProcess(String processId, String staffId, Map<String, Object> variables) {
        identityService.setAuthenticatedUserId(staffId);
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(processId).singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(definition.getKey(), variables);
        log.info("===>启动流程{}", processInstance);
        //查询当前的inst task
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();
        log.info("===>task info:{}", JSONUtil.toJsonPrettyStr(task));

        String taskDefinitionKey = task.getTaskDefinitionKey();
        //如果部门领导人
        if ("dept_leader".equals(taskDefinitionKey)) {
            try {
                StaffEntity deptLeader = staffQueryService.getDeptLeader(staffId);
                task.setAssignee(deptLeader.getStaffId());
                MessageEntity entity = new MessageEntity();
                entity.setReceiverId(deptLeader.getStaffId());
                entity.setReceiverName(deptLeader.getStaffName());
                msgManagementService.addMsgEntity(entity);
            } catch (NoRecordException e) {
                log.info("===>NoRecordException", e);
            }

        }
    }

    /**
     * 分页查询任务列表
     *
     * @param startedBy
     * @param page
     * @return
     */
    public PageWrapper<TaskEntity> getPageTasks(String startedBy, Pagination page) {
        List<HistoricProcessInstance> result = historyService.createHistoricProcessInstanceQuery().startedBy(startedBy)
                .listPage((page.getPageIndex() - 1) * page.getPageSize(), page.getPageSize());
        long total = historyService.createHistoricProcessInstanceQuery().startedBy(startedBy).count();
        List<TaskEntity> dataList = new ArrayList<>();
        result.forEach(v -> {
                    //查询当前节点名称
                    Task task = taskService.createTaskQuery().processInstanceId(v.getId()).active().singleResult();
                    TaskEntity entity = new TaskEntity();
                    entity.setInstId(v.getId());
                    if (null != task) {
                        entity.setTaskNodeName(task.getName());
                    }
            entity.setCreateTime(task.getCreateTime());
                    dataList.add(entity);
                }
        );
        page.setTotalRecords((int) total);
        page.setPageIndex(page.getPageIndex() + 1);
        PageWrapper<TaskEntity> resultPage = new PageWrapper<>(page);
        resultPage.setRows(dataList);
        return resultPage;
    }
}
