package com.handy.controller.admin.setting;

import com.handy.common.constants.ScheduleTaskEnum;
import com.handy.service.service.task.IScheduleTaskService;
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
 * @date 2019/9/5 16:50
 */
@Controller
@RequestMapping("/admin/setting/task")
@ApiIgnore()
public class ScheduleTaskController {
    @Autowired
    private IScheduleTaskService scheduleTaskService;

    @GetMapping("/list")
    public String list() {
        return "admin/setting/task/list";
    }

    @GetMapping("/view")
    public String view(Model model, Long id) {
        val scheduleTask = scheduleTaskService.getById(id);
        model.addAttribute("scheduleTask", scheduleTask);
        return "admin/setting/task/view";
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        val scheduleTask = scheduleTaskService.getById(id);
        model.addAttribute("TaskEnum", ScheduleTaskEnum.values());
        model.addAttribute("scheduleTask", scheduleTask);
        return "admin/setting/task/edit";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("TaskEnum", ScheduleTaskEnum.values());
        return "admin/setting/task/add";
    }

}
