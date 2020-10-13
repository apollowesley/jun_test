package org.iartisan.admin.template.controller.support.rest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.ResourceSupportService;
import org.iartisan.admin.template.authentication.RoleSupportService;
import org.iartisan.admin.template.authentication.service.entity.RoleEntity;
import org.iartisan.admin.template.authentication.service.entity.ZTreeEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author King
 * @since 2018/2/28
 */
@RestController
@RequestMapping("roleSupport")
public class RoleSupportRestController extends BaseController implements ISupportRestController<RoleEntity> {


    @Autowired
    private RoleSupportService roleSupportService;

    @Autowired
    private ResourceSupportService resourceSupportService;


    @RequiresPermissions("auth:manage:role:deleteData")
    @PostMapping(ReqContants.REQ_DELETE_DATA)
    public WebR deleteData(String roleId) {
        roleSupportService.deleteRole(roleId);
        WebR r = new WebR();
        r.setMessage("删除角色成功");
        return r;
    }

    @GetMapping("getResourceData")
    public WebR getResourceData() {
        List<ZTreeEntity> dataList = resourceSupportService.getResourceTree();
        WebR r = new WebR();
        r.setData(dataList);
        return r;
    }

    @PostMapping("getResourceListByRoleId")
    public WebR getResourceListByRoleId(String roleId, boolean chkDisabled) {
        List<ZTreeEntity> dataList = resourceSupportService.getResourceListByRoleId(roleId, chkDisabled);
        WebR r = new WebR();
        r.setData(dataList);
        return r;
    }

    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(Pagination page, String roleName) {
        PageWrapper<RoleEntity> pageData = roleSupportService.getRolePageData(page, roleName);
        WebR webR = new WebR(pageData.getPage());
        webR.setData(pageData.getRows());
        return webR;
    }


    @PostMapping(ReqContants.REQ_MODIFY_DATA)
    public WebR modifyData(RoleEntity roleEntity) {
        roleSupportService.modifyRole(roleEntity);
        WebR r = new WebR();
        return r;
    }

    @RequiresPermissions("auth:manage:role:addData")
    @PostMapping(ReqContants.REQ_ADD_DATA)
    public WebR addData(RoleEntity roleEntity) {
        roleSupportService.addRole(roleEntity);
        WebR r = new WebR();
        return r;
    }

}
