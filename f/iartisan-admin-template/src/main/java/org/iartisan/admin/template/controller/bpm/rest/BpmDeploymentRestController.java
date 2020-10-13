package org.iartisan.admin.template.controller.bpm.rest;


import cn.hutool.core.io.IoUtil;
import org.flowable.engine.repository.Deployment;
import org.iartisan.admin.template.service.bpm.DeploymentManagement;
import org.iartisan.admin.template.service.bpm.ProcessDefinitionManagement;
import org.iartisan.admin.template.service.bpm.entity.DeploymentEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *
 * @author King
 * @since 2018/6/28
 */
@RestController
@RequestMapping("bpm/deployment")
public class BpmDeploymentRestController extends BaseController implements ISupportRestController<Deployment> {

    @Autowired
    private DeploymentManagement deploymentManagement;

    @Autowired
    private ProcessDefinitionManagement processDefinitionManagement;

    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(Pagination page) {
        PageWrapper<DeploymentEntity> pageData = deploymentManagement.queryDeploymentsPage(page);
        WebR webR = new WebR(pageData.getPage());
        webR.setData(pageData.getRows());
        return webR;
    }

    @PostMapping(ReqContants.REQ_DELETE_DATA)
    @Override
    public WebR deleteData(String deploymentId) {
        WebR webR = new WebR();
        try {
            deploymentManagement.delete(deploymentId);
        } catch (Exception e) {
            logger.error("bmp deloyment delete error ", e);
            webR.isError("该流程下已有事项，不允许删除");
        }
        return webR;
    }

    @Override
    public WebR modifyData(Deployment deployment) {
        return null;
    }

    @Override
    public WebR addData(Deployment deployment) {
        return null;
    }

    /**
     * 通过附件部署流程
     *
     * @param request
     * @return
     */
    @PostMapping(value = "deploy")
    public WebR deploy(HttpServletRequest request) {
        WebR r = new WebR();
        try {
            StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getMultiFileMap().getFirst("file");
            deploymentManagement.deploy(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            return r;
        } catch (Exception ex) {
            logger.error("Exception", ex);
            r.isError(ex.getMessage());
            return r;
        }
    }

    /**
     * 查询流程定义
     *
     * @return
     */
    @GetMapping("getDefinitionDetail")
    public void getDefinitionDetail(HttpServletResponse response, String deploymentId) throws IOException {
        InputStream diagram = processDefinitionManagement.getModelDiagramById(deploymentId);
        ServletOutputStream out = response.getOutputStream();
        try {
            out.write(IoUtil.readBytes(diagram));
        } finally {
            IoUtil.close(diagram);
            IoUtil.close(out);
        }
    }
}
