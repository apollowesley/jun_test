package com.handy.activiti.service.impl;

import com.handy.activiti.service.IWorkFlowRuntimeService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 9:42
 */
@Service
public class WorkFlowRuntimeServiceImpl implements IWorkFlowRuntimeService {

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 启动流程
     *
     * @param processDefinitionKey 流程key
     * @param paramsMap            参数
     * @return
     */
    @Override
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> paramsMap) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, paramsMap);
    }
}
