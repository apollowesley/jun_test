package com.handy.activiti.service.impl;

import com.handy.activiti.service.IWorkFlowRepositoryService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/11 18:24
 */
@Service
public class WorkFlowRepositoryServiceImpl implements IWorkFlowRepositoryService {
    /**
     * 流程存储服务
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 流程部署列表
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Deployment> deployList(Integer page, Integer limit) {
        return repositoryService.createDeploymentQuery().orderByDeploymentId().desc().listPage((page - 1), limit);
    }

    /**
     * 获取模型列表
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<ProcessDefinition> getProcessList(Integer page, Integer limit) {
        return repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc().listPage((page - 1), limit);
    }

    /**
     * 查看定义的流程图
     *
     * @param processDefinitionId
     * @return
     */
    @Override
    public byte[] definitionImage(String processDefinitionId) {
        try {
            BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
            if (model != null && model.getLocationMap().size() > 0) {
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                InputStream imageStream = generator.generateDiagram(model, "png", new ArrayList<>());
                byte[] buffer = new byte[imageStream.available()];
                imageStream.read(buffer);
                imageStream.close();
                return buffer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
