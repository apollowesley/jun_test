package com.handy.controller.admin.workflow;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.handy.service.entity.license.LicenseEvent;
import com.handy.service.service.license.ILicenseEventService;
import com.handy.service.service.license.ILicenseFormService;
import com.handy.service.service.sys.ISysAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 11:40
 */
@Controller
@RequestMapping("/admin/workflow")
@ApiIgnore()
public class WorkFlowController {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private ILicenseEventService licenseEventService;
    @Autowired
    private ILicenseFormService licenseFormService;

    @GetMapping("/list")
    public String list() {
        return "admin/workflow/list";
    }

    @GetMapping("/model/list")
    public String modelList() {
        return "admin/workflow/model/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        val userList = sysAccountService.list();
        model.addAttribute("userList", userList);
        return "admin/workflow/add";
    }

    @GetMapping("/task/list")
    public String taskList() {
        return "admin/workflow/task/list";
    }

    @GetMapping("/task/approve")
    public String approve(Model model, Long eventId) {
        // 流程信息
        val wrapper = new QueryWrapper<LicenseEvent>();
        LambdaQueryWrapper<LicenseEvent> queryWrapper = wrapper.lambda();
        queryWrapper.eq(LicenseEvent::getId, eventId);
        val licenseEvent = licenseEventService.getOne(wrapper);
        model.addAttribute("licenseEvent", licenseEvent);
        // 流程表单信息
        val licenseForm = licenseFormService.getById(licenseEvent.getFormId());
        model.addAttribute("licenseForm", licenseForm);
        return "admin/workflow/task/approve";
    }

    @GetMapping("/task/pass/view")
    public String passView(Model model, Long eventId) {
        // 流程信息
        val wrapper = new QueryWrapper<LicenseEvent>();
        LambdaQueryWrapper<LicenseEvent> queryWrapper = wrapper.lambda();
        queryWrapper.eq(LicenseEvent::getId, eventId);
        val licenseEvent = licenseEventService.getOne(wrapper);
        model.addAttribute("licenseEvent", licenseEvent);
        // 流程表单信息
        val licenseForm = licenseFormService.getById(licenseEvent.getFormId());
        model.addAttribute("licenseForm", licenseForm);
        return "admin/workflow/task/passView";
    }

    @GetMapping("/task/reject/view")
    public String rejectView(Model model, Long eventId) {
        // 流程信息
        val wrapper = new QueryWrapper<LicenseEvent>();
        LambdaQueryWrapper<LicenseEvent> queryWrapper = wrapper.lambda();
        queryWrapper.eq(LicenseEvent::getId, eventId);
        val licenseEvent = licenseEventService.getOne(wrapper);
        model.addAttribute("licenseEvent", licenseEvent);
        // 流程表单信息
        val licenseForm = licenseFormService.getById(licenseEvent.getFormId());
        model.addAttribute("licenseForm", licenseForm);
        return "admin/workflow/task/rejectView";
    }
}
