package org.iartisan.admin.template.service.bpm;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;

/**
 * @author King  2019-8-30
 */
@Service
public class ProcessDefinitionManagement {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    public InputStream getModelDiagramById(String deployId) {
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId)
                .singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        return processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel,
                "png", Collections.<String>emptyList(),
                Collections.<String>emptyList(),
                "宋体", "宋体", "宋体",
                null, 1.0, false);
    }
}
