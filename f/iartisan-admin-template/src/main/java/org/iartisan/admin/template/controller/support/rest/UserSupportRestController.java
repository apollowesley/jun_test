package org.iartisan.admin.template.controller.support.rest;

import afu.org.checkerframework.checker.oigj.qual.O;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.iartisan.admin.template.authentication.UserSupportService;
import org.iartisan.admin.template.authentication.service.entity.UserEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * user support
 *
 * @author King
 * @since 2018/2/26
 */
@RestController
@RequestMapping("userSupport")
public class UserSupportRestController extends BaseController implements ISupportRestController<UserEntity> {

    @Autowired
    private UserSupportService userSupportService;


    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(Pagination page, String userName) {
        PageWrapper<UserEntity> pageData = userSupportService.getUserPageData(page, userName);
        WebR webR = new WebR(pageData.getPage());
        webR.setData(pageData.getRows());
        return webR;
    }

    @Override
    @RequiresPermissions("auth:manage:user:addData")
    @PostMapping(ReqContants.REQ_ADD_DATA)
    public WebR addData(UserEntity entity) {
        userSupportService.addUser(entity);
        WebR r = new WebR();
        return r;
    }

    @Override
    @RequiresPermissions("auth:manage:user:modifyData")
    @PostMapping(ReqContants.REQ_MODIFY_DATA)
    public WebR modifyData(UserEntity entity) {
        userSupportService.modifyData(entity);
        WebR r = new WebR();
        return r;
    }

    @RequiresPermissions("auth:manage:user:changeStatus")
    @PostMapping("changeStatus")
    public WebR changeStatus(String userId, String status) {
        userSupportService.changeStatus(userId, status);
        WebR r = new WebR();
        return r;
    }

    @Override
    @RequiresPermissions("auth:manage:user:deleteData")
    @PostMapping(ReqContants.REQ_DELETE_DATA)
    public WebR deleteData(String userId) {
        userSupportService.deleteByUserId(userId);
        WebR r = new WebR();
        return r;
    }

    @PostMapping("modifyPwd")
    public WebR modifyPwd(String pwd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(getCustId());
        userEntity.setUserPwd(pwd);
        userSupportService.modifyPwd(userEntity);
        WebR r = new WebR();
        return r;
    }
}
