package org.iartisan.admin.template.controller.bpm.page;


import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportPageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>
 *
 * @author King
 * @since 2018/6/28
 */
@Controller
@RequestMapping("bpm/model")
public class BpmModelPageController extends BaseController implements ISupportPageController {


    @Override
    public String index() {
        return null;
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
