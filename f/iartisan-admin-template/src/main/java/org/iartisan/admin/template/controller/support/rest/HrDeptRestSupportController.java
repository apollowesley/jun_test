package org.iartisan.admin.template.controller.support.rest;

import org.iartisan.admin.template.authentication.service.entity.ZTreeEntity;
import org.iartisan.admin.template.service.entity.DeptEntity;
import org.iartisan.admin.template.service.management.DeptManagementService;
import org.iartisan.admin.template.service.query.DeptQueryService;
import org.iartisan.runtime.exception.NotAllowedException;
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
 * 部门管理页面
 *
 * @author King  2019-7-17
 */
@RestController
@RequestMapping("hrDept")
public class HrDeptRestSupportController extends BaseController implements ISupportRestController<DeptEntity> {

    @Autowired
    private DeptQueryService bizDeptQueryService;

    @Autowired
    private DeptManagementService deptManagementService;

    @Override
    @GetMapping(ReqContants.REQ_DELETE_DATA)
    public WebR deleteData(String deptId) {
        //如果deptId 是root节点则不允许删除
        WebR webR = new WebR();
        try {
            deptManagementService.deleteData(deptId);
        } catch (NotAllowedException e) {
            webR.isError(e.getMessage());
        }
        return webR;
    }

    @Override
    @PostMapping(ReqContants.REQ_MODIFY_DATA)
    public WebR modifyData(DeptEntity bizDeptEntity) {
        deptManagementService.updateData(bizDeptEntity);
        return new WebR();
    }

    @Override
    @PostMapping(ReqContants.REQ_ADD_DATA)
    public WebR addData(DeptEntity bizDeptEntity) {
        deptManagementService.addData(bizDeptEntity);
        //添加部门
        return new WebR();
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @GetMapping(value = ReqContants.REQ_QUERY_LIST_DATA)
    public WebR getDataList() {
        WebR webR = new WebR();
        List<ZTreeEntity> dataList = bizDeptQueryService.getDeptList();
        webR.setData(dataList);
        return webR;
    }
}
