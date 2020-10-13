package org.iartisan.admin.template.controller.bpm.demo;

import org.iartisan.admin.template.service.bpm.DeploymentManagement;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author King  2019-8-30
 */
@Controller
@RequestMapping("bpm/demo")
public class BpmDemoPageController extends BaseController {

    private static final String VIEW_PREFIX = "bpm/demo/new_item/";

    @Autowired
    private DeploymentManagement deploymentManagement;

    @GetMapping("newItem/index")
    public String newItemIndex() {
        return VIEW_PREFIX + "newItem_index";
    }

    @GetMapping("newItem/" + ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model) {
        model.addAttribute("deployments", deploymentManagement.getAllDeployments());
        return VIEW_PREFIX + "newItem_add";
    }

}
