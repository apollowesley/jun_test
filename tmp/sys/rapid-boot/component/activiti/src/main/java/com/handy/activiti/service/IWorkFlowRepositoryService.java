package com.handy.activiti.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/11 18:24
 */
public interface IWorkFlowRepositoryService {
    /**
     * 流程部署列表
     *
     * @param page
     * @param limit
     * @return
     */
    List<Deployment> deployList(Integer page, Integer limit);

    /**
     * 获取模型列表
     *
     * @param page
     * @param limit
     * @return
     */
    List<ProcessDefinition> getProcessList(Integer page, Integer limit);

    /**
     * 查看定义的流程图
     *
     * @param processDefinitionId
     * @return
     */
    byte[] definitionImage(String processDefinitionId);
}
