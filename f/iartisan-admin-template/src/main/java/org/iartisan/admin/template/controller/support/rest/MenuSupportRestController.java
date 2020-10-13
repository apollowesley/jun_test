package org.iartisan.admin.template.controller.support.rest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.MenuSupportService;
import org.iartisan.admin.template.authentication.service.entity.MenuEntity;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.authentication.MenuTree;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * menu support
 *
 * @author King
 * @since 2018/2/26
 */
@RestController
@RequestMapping("menuSupport")
public class MenuSupportRestController extends BaseController implements ISupportRestController<MenuEntity>{


    @Autowired
    private MenuSupportService menuSupportService;


    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(String menuName) {
        List<MenuTree> dataList = menuSupportService.getMenuPageData(menuName);
        WebR webR = new WebR();
        webR.setData(dataList);
        return webR;
    }

    @RequiresPermissions("auth:manage:menu:addData")
    @PostMapping(ReqContants.REQ_ADD_DATA)
    public WebR addData(MenuEntity menuEntity) {
        menuSupportService.addMenu(menuEntity);
        WebR webR = new WebR();
        return webR;
    }

    @RequiresPermissions("auth:manage:menu:deleteData")
    @PostMapping(ReqContants.REQ_DELETE_DATA)
    public WebR deleteData(String menuId) {
        menuSupportService.deleteData(menuId);
        WebR webR = new WebR();
        return webR;
    }


    @RequiresPermissions("auth:manage:menu:modifyData")
    @PostMapping(ReqContants.REQ_MODIFY_DATA)
    public WebR modifyData(MenuEntity menuEntity) {
        menuSupportService.modifyData(menuEntity);
        WebR webR = new WebR();
        return webR;
    }
}
