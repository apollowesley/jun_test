package org.iartisan.admin.template.controller.support.page;

import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 菜单权限管理
 *
 * @author King
 * @since 2018/3/30
 */
@Controller
@RequestMapping("resourceSupport")
public class ResourceSupportController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "resource/";

    @GetMapping(ReqContants.REQ_INDEX)
    public String index(Model model, String menuId) {
        model.addAttribute(_data, menuId);
        return VIEW_PREFIX + "resource_index";
    }

    @Override
    public String index() {
        return null;
    }

    @GetMapping(ReqContants.REQ_ADD_DATA_PAGE)
    public String addDataPage(Model model, String menuId) {
        model.addAttribute(_data, menuId);
        return VIEW_PREFIX + "resource_add";
    }


    public String addDataPage(Model model) {
        return null;
    }

    @Override
    public String modifyDataPage(Model model, String keyId) {
        return null;
    }

    @Override
    public String queryDetailPage(Model model, String keyId) {
        return null;
    }
}
