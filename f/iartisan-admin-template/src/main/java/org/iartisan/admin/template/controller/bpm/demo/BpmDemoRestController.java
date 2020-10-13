package org.iartisan.admin.template.controller.bpm.demo;

import com.google.common.collect.Maps;
import org.iartisan.admin.template.authentication.UserSupportService;
import org.iartisan.admin.template.service.bpm.BpmWorkflowManagement;
import org.iartisan.admin.template.service.bpm.entity.LeaveEntity;
import org.iartisan.admin.template.service.bpm.entity.TaskEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author King  2019-8-30
 */
@RestController
@RequestMapping("bpm/demo")
public class BpmDemoRestController extends BaseController {

    @Autowired
    private BpmWorkflowManagement bpmWorkflowManagement;

    @Autowired
    private UserSupportService userSupportService;

    @PostMapping("newItem/" + ReqContants.REQ_ADD_DATA)
    public WebR addData(LeaveEntity leaveEntity) {
        WebR webR = new WebR();
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("beginTime", leaveEntity.getBeginTime());
        variables.put("endTime", leaveEntity.getEndTime());
        variables.put("days", leaveEntity.getDays());
        variables.put("reason", leaveEntity.getReason());
        bpmWorkflowManagement.startProcess(leaveEntity.getProcessId(), userSupportService.getStaffId(getCustId()), variables);
        return webR;
    }

    /**
     * 分页查询自己发起的任务
     *
     * @return
     */
    @PostMapping("myTask/" + ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryOwnerPageTasks(Pagination page) {
        PageWrapper<TaskEntity> pageResult = bpmWorkflowManagement.getPageTasks(userSupportService.getStaffId(getCustId()), page);
        WebR r = new WebR(pageResult.getPage());
        r.setData(pageResult.getRows());
        return r;
    }


}
