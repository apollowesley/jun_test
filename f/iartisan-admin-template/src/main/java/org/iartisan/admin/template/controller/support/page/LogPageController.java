package org.iartisan.admin.template.controller.support.page;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.MenuSupportService;
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
 * log support
 * 日志管理
 *
 * @author King
 * @since 2018/2/26
 */
@Controller
@RequestMapping("log")
public class LogPageController extends BaseController implements ISupportPageController {

    private static final String VIEW_PREFIX = "log/";


    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "log_index";
    }

    @Override
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
