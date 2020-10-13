package org.iartisan.admin.template.controller;

import org.iartisan.admin.template.enums.PageModelEnum;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.utils.WebUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 装饰页面
 *
 * @author King
 * @since 2017/11/2
 */
@Controller
public class DecoratorController extends BaseController {

    private static final String PAGE_MAIN = "main";
    @Value("${iartisan.admin.authenticate.login:login}")
    private String loginPage;
    @Value("${iartisan.admin.authenticate.decorator:_include/decorator}")
    private String decoratorPage;

    @RequestMapping(ReqContants.REQ_LOGOUT)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return loginPage;
    }

    @RequestMapping(ReqContants.REQ_DECORATOR)
    public String decorator() {
        return decoratorPage;
    }

    @GetMapping(ReqContants.REQ_LOGIN)
    public String login() {
        return loginPage;
    }

    @GetMapping(ReqContants.REQ_INDEX)
    public String index() {
        String model = (String) WebUtil.getShiroSession().getAttribute("_pageModel");
        if (model.equals(PageModelEnum.single_page.name())) {
            return "single.index";
        }
        return "index";
    }

    @GetMapping("changeTheme")
    public String changeTheme() {
        String model = (String) WebUtil.getShiroSession().getAttribute("_pageModel");
        if (model.equals(PageModelEnum.iframe.name())) {
            WebUtil.getShiroSession().setAttribute("_pageModel", PageModelEnum.single_page.name());
        } else if (model.equals(PageModelEnum.single_page.name())) {
            WebUtil.getShiroSession().setAttribute("_pageModel", PageModelEnum.iframe.name());
        }
        return _redirect + "index";
    }

    @GetMapping(ReqContants.REQ_MAIN)
    public String main() {
        return PAGE_MAIN;
    }

}
