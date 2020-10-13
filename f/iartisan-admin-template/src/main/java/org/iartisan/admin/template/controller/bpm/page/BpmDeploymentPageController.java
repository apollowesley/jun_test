package org.iartisan.admin.template.controller.bpm.page;


import org.iartisan.admin.template.service.bpm.DeploymentManagement;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>
 *
 * @author King
 * @since 2018/6/28
 */
@Controller
@RequestMapping("bpm/deployment")
public class BpmDeploymentPageController extends BaseController {

    private static final String VIEW_PREFIX = "bpm/deployment/";

    @Autowired
    private DeploymentManagement deploymentManagement;

    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "deployment_index";
    }

    @GetMapping("toDesign")
    public String toDesign() {
        return "redirect:/modler/index.html#/processes";
    }
}
