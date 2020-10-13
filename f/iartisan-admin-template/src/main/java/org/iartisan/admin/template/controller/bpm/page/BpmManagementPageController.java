package org.iartisan.admin.template.controller.bpm.page;

import org.iartisan.admin.template.service.bpm.DeploymentManagement;
import org.iartisan.admin.template.service.bpm.BpmWorkflowManagement;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *
 * @author King
 * @since 2018/6/28
 */
@Controller
@RequestMapping("bpm/workflow")
public class BpmManagementPageController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "activiti/workflow/";

    @Autowired
    private DeploymentManagement deploymentManagement;

    @Autowired
    private BpmWorkflowManagement workflowManagement;


    @Override
    @GetMapping("start" + ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "start/workflowStart_index";
    }

    @Override
    @GetMapping("start/" + ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model) {
        //查询所有流程列表
        model.addAttribute("deployments", deploymentManagement.getAllDeployments());
        return VIEW_PREFIX + "start/workflowStart_add";
    }

    @Override
    public String modifyDataPage(Model model, String s) {
        return null;
    }

    @Override
    public String queryDetailPage(Model model, String s) {
        return null;
    }


}
