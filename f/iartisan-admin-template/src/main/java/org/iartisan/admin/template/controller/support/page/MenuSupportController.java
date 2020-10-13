package org.iartisan.admin.template.controller.support.page;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.MenuSupportService;
import org.iartisan.runtime.web.annotation.WebLog;
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
 * menu support
 *
 * @author King
 * @since 2018/2/26
 */
@Controller
@RequestMapping("menuSupport")
public class MenuSupportController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "menu/";

    @Autowired
    private MenuSupportService menuSupportService;


    @WebLog("菜单管理初始页面")
    @RequiresPermissions("auth:manage:menu:index")
    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "menu_index";
    }

    @RequiresPermissions("auth:manage:menu:addDataPage")
    @GetMapping(ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model) {
        //查询一级菜单
        model.addAttribute(_data, menuSupportService.getFirstMenus());
        return VIEW_PREFIX + "menu_add";
    }

    @RequiresPermissions("auth:manage:menu:modifyDataPage")
    @GetMapping(ReqContants.REQ_MODIFY_DATA_PAGE)
    public String modifyDataPage(Model model, String menuId) {
        //查询一级菜单
        model.addAttribute("firstMenus", menuSupportService.getFirstMenus());
        //查询一级菜单
        model.addAttribute(_data, menuSupportService.getMenuById(menuId.toString()));
        return VIEW_PREFIX + "menu_modify";
    }

    @Override
    public String queryDetailPage(Model model, String keyId) {
        return null;
    }
}
