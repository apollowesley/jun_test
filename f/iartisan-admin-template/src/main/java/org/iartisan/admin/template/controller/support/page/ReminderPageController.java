package org.iartisan.admin.template.controller.support.page;

import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 提醒页面controller
 *
 * @author King  2019-9-29
 */
@Controller
@RequestMapping("reminder")
public class ReminderPageController implements ISupportPageController {

    private static final String VIEW_PREFIX = "sysMsg/";

    @Override
    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        return VIEW_PREFIX + "sysMsg_index";
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
