package org.iartisan.admin.template.service.bpm;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.iartisan.admin.template.service.bpm.entity.DeploymentEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流程部署
 *
 * @author King
 * @since 2018/6/28
 */
@Service
public class DeploymentManagement {

    @Autowired
    private RepositoryService repositoryService;

    public List<DeploymentEntity> getAllDeployments() {
        List<Deployment> result = repositoryService.createDeploymentQuery().list();
        return convertDeployment(result);
    }

    public List<DeploymentEntity> convertDeployment(List<Deployment> dataList) {
        List<DeploymentEntity> deploymentEntities = new ArrayList<>();
        dataList.forEach(v -> {
            DeploymentEntity entity = new DeploymentEntity();
            entity.setId(v.getId());
            entity.setName(v.getName());
            entity.setCategory(v.getCategory());
            entity.setDeploymentTime(v.getDeploymentTime());
            entity.setTenantId(v.getTenantId());
            deploymentEntities.add(entity);
        });
        return deploymentEntities;
    }

    public PageWrapper<DeploymentEntity> queryDeploymentsPage(Pagination page) {
        long total = repositoryService.createDeploymentQuery().count();
        List<Deployment> result = repositoryService.createDeploymentQuery().orderByDeploymenTime().asc().listPage(
                (page.getPageIndex() - 1) * page.getPageSize()
                , page.getPageSize());
        page.setTotalRecords((int) total);
        page.setPageIndex(page.getPageIndex() + 1);
        PageWrapper<DeploymentEntity> resultPage = new PageWrapper<>(page);
        resultPage.setRows(convertDeployment(result));
        return resultPage;
    }


    public void deploy(String fileName, InputStream inputStream) {
        repositoryService.createDeployment().addInputStream(fileName, inputStream).name(fileName).deploy();
    }

    public void delete(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }

    //挂起流程
    public void suspend(String deploymentId) {
        //通过deloyemntId 查询defineId
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

        //repositoryService.suspendProcessDefinitionById();
    }
}
