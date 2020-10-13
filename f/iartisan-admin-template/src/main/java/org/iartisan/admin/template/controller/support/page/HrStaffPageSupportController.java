package org.iartisan.admin.template.controller.support.page;

import org.iartisan.admin.template.service.entity.DeptEntity;
import org.iartisan.admin.template.service.query.DeptQueryService;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 员工管理页面
 *
 * @author King  2019-7-17
 */
@Controller
@RequestMapping("hrStaff")
public class HrStaffPageSupportController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "hrStaff/";

    @Autowired
    private DeptQueryService deptQueryService;

    @Override
    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "hrStaff_index";
    }

    /**
     * 跳转员工管理页面
     *
     * @param deptId
     * @return
     */
    @GetMapping("staffManagerPage")
    public String staffManagerPage(Model model, String deptId) {
        //查询部门信息
        DeptEntity deptEntity = deptQueryService.getDataById(deptId);
        model.addAttribute("deptEntity", deptEntity);
        return VIEW_PREFIX + "hrStaff_manager";
    }

    @Override
    public String addDataPage(Model model) {
        return null;
    }

    /**
     * 添加员工
     *
     * @param model
     * @param deptId
     * @return
     */
    @GetMapping(value = ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model, String deptId) {
        //查询部门信息
        DeptEntity deptEntity = deptQueryService.getDataById(deptId);
        model.addAttribute("deptEntity", deptEntity);
        return VIEW_PREFIX + "hrStaff_add";
    }


    @Override
    @GetMapping(ReqContants.REQ_MODIFY_DATA_PAGE)
    public String modifyDataPage(Model model, String deptId) {
        return VIEW_PREFIX + "hrStaff_modify";
    }


    @Override
    @GetMapping(ReqContants.REQ_QUERY_DETAIL_PAGE)
    public String queryDetailPage(Model model, String deptId) {
        return VIEW_PREFIX + "hrStaff_detail";
    }
}
