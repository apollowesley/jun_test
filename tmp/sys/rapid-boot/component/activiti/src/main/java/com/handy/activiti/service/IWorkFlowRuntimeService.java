package com.handy.activiti.service;

import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 9:42
 */
public interface IWorkFlowRuntimeService {
    /**
     * 启动流程
     *
     * @param processDefinitionKey 流程key
     * @param paramsMap            参数
     * @return
     */
    ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> paramsMap);
}
