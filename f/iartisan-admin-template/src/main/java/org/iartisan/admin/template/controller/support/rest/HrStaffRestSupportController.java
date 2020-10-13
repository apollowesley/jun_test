package org.iartisan.admin.template.controller.support.rest;

import org.iartisan.admin.template.authentication.UserSupportService;
import org.iartisan.admin.template.authentication.service.entity.UserEntity;
import org.iartisan.admin.template.service.entity.StaffEntity;
import org.iartisan.admin.template.service.management.StaffManagementService;
import org.iartisan.admin.template.service.query.StaffQueryService;
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

/**
 * 部门管理页面
 *
 * @author King  2019-7-17
 */
@RestController
@RequestMapping("hrStaff")
public class HrStaffRestSupportController extends BaseController implements ISupportRestController<StaffEntity> {

    @Autowired
    private StaffQueryService staffQueryService;

    @Autowired
    private StaffManagementService staffManagementService;

    @Autowired
    private UserSupportService userSupportService;

    @Override
    @GetMapping(ReqContants.REQ_DELETE_DATA)
    public WebR deleteData(String staffId) {
        WebR webR = new WebR();
        staffManagementService.deleteByStaffId(staffId);
        return webR;
    }

    @Override
    public WebR modifyData(StaffEntity staffEntity) {
        return null;
    }

    @Override
    @PostMapping(ReqContants.REQ_ADD_DATA)
    public WebR addData(StaffEntity staffEntity) {
        WebR webR = new WebR();
        staffManagementService.saveData(staffEntity);
        return webR;
    }

    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR getPageData(Pagination page, StaffEntity staffEntity) {
        PageWrapper<StaffEntity> pageDataByWrapper = staffQueryService.getPageData(page, staffEntity);
        WebR webR = new WebR(pageDataByWrapper.getPage());
        webR.setData(pageDataByWrapper.getRows());
        return webR;
    }

    /**
     * 更新员工状态
     * @param staffId
     * @return
     */
    @GetMapping("updateStaffStatus")
    public WebR updateStaffStatus(String staffId) {
        WebR webR = new WebR();
        staffManagementService.updateStaffStatus(staffId);
        return webR;
    }

    /**
     * 分配登录权限
     *
     * @param staffId
     * @return
     */
    @GetMapping("assignLoginPermissions")
    public WebR assignLoginPermissions(String staffId) {
        WebR webR = new WebR();
        UserEntity userEntity = new UserEntity();
        userEntity.setStaffId(staffId);
        userSupportService.addUser(userEntity);
        return webR;
    }
}
