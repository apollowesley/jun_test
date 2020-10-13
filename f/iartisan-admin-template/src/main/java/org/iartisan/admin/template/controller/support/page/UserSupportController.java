package org.iartisan.admin.template.controller.support.page;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.RoleSupportService;
import org.iartisan.runtime.web.annotation.WebLog;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * user support
 *
 * @author King
 * @since 2018/2/26
 */
@Controller
@RequestMapping("userSupport")
public class UserSupportController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "user/";

    @Autowired
    private RoleSupportService roleSupportService;


    @WebLog("用户管理初始页面")
    @RequiresPermissions("auth:manage:user:index")
    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "user_index";
    }

    @RequiresPermissions("auth:manage:user:addDataPage")
    @GetMapping(ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model) {
        //查询所有的角色列表
        model.addAttribute(_data, roleSupportService.getAllRoles());
        return VIEW_PREFIX + "user_add";
    }


    @RequiresPermissions("auth:manage:user:modifyDataPage")
    @GetMapping(ReqContants.REQ_MODIFY_DATA_PAGE)
    public String modifyDataPage(Model model, String userId) {
        //查询用户所有角色
        model.addAttribute(_data, roleSupportService.getRoleByUserId(userId.toString()));
        return VIEW_PREFIX + "user_modify";
    }

    @GetMapping(ReqContants.REQ_QUERY_DETAIL_PAGE)
    public String queryDetailPage(Model model, String userId) {
        //查询用户所有角色
        model.addAttribute(_data, roleSupportService.getRoleByUserId(userId));
        return VIEW_PREFIX + "user_detail";
    }

    @GetMapping("modifyPwdPage")
    public String modifyPwdPage() {
        return VIEW_PREFIX + "user_modifyPwd";
    }
}
