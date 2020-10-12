package com.handy.controller.admin.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.common.constants.ScheduleTaskEnum;
import com.handy.common.vo.PageVO;
import com.handy.common.vo.ResultVO;
import com.handy.config.ScheduleTaskConfig;
import com.handy.controller.BaseController;
import com.handy.service.entity.task.ScheduleTask;
import com.handy.service.service.task.IScheduleTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/5 16:50
 */
@RestController
@RequestMapping("/api/admin/setting/task")
@Api(value = "定时任务")
public class ScheduleTaskApiController extends BaseController {
    @Autowired
    private IScheduleTaskService scheduleTaskService;
    @Autowired
    private ScheduleTaskConfig scheduleTaskConfig;

    @GetMapping("/list")
    @ApiOperation(value = "获取列表数据")
    public PageVO list(@ApiParam(name = "page", value = "每页页数") Long page,
                       @ApiParam(name = "limit", value = "每页条数") Long limit) {
        val wrapper = new QueryWrapper<ScheduleTask>();
        LambdaQueryWrapper<ScheduleTask> queryWrapper = wrapper.lambda();
        queryWrapper.orderByDesc(ScheduleTask::getCreateTime);
        IPage<ScheduleTask> scheduleTaskIPage = scheduleTaskService.page(new Page<>(page, limit), wrapper);
        return PageVO.pageVO(scheduleTaskIPage.getRecords(), scheduleTaskIPage.getTotal());
    }

    @PutMapping("/confirm/edit/status")
    @ApiOperation(value = "是否启用状态更改")
    public ResultVO confirmEditStatus(@ApiIgnore() HttpSession session,
                                      @ApiParam(name = "id", value = "主键id") Long id,
                                      @ApiParam(name = "status", value = "启用状态") Boolean status) {
        val scheduleTask = new ScheduleTask();
        scheduleTask.setId(id);
        scheduleTask.setStatus(status);
        scheduleTask.setMender(getLoginUser(session).getCode());
        val rst = scheduleTaskService.updateById(scheduleTask);
        if (rst) {
            runTask();
        }
        return rst ? ResultVO.success("更新启用状态成功") : ResultVO.success("更新启用状态失败");
    }

    @DeleteMapping("/confirm/delete/{id}")
    @ApiOperation(value = "任务删除")
    public ResultVO confirmDelete(@ApiParam(name = "id", value = "主键id") Long id) {
        val rst = scheduleTaskService.removeById(id);
        if (rst) {
            runTask();
        }
        return rst ? ResultVO.success("删除成功") : ResultVO.success("删除失败");
    }

    @PostMapping("/confirm/add")
    @ApiOperation(value = "任务新增")
    public ResultVO confirmAdd(@ApiIgnore() HttpSession session, ScheduleTask scheduleTask) {
        scheduleTask.setClazzPathName(ScheduleTaskEnum.getEnum(scheduleTask.getClazzPathId()).getPackageName());
        scheduleTask.setCreator(getLoginUser(session).getCode());
        val rst = scheduleTaskService.save(scheduleTask);
        if (rst) {
            runTask();
        }
        return rst ? ResultVO.success("新增成功") : ResultVO.success("新增失败");
    }

    @PutMapping("/confirm/edit")
    @ApiOperation(value = "任务编辑")
    public ResultVO confirmEdit(@ApiIgnore() HttpSession session, ScheduleTask scheduleTask) {
        scheduleTask.setClazzPathName(ScheduleTaskEnum.getEnum(scheduleTask.getClazzPathId()).getPackageName());
        scheduleTask.setMender(getLoginUser(session).getCode());
        val rst = scheduleTaskService.updateById(scheduleTask);
        if (rst) {
            runTask();
        }
        return rst ? ResultVO.success("更新成功") : ResultVO.success("更新失败");
    }

    /**
     * 重新运行定时任务
     */
    private void runTask() {
        List<ScheduleTask> list = scheduleTaskService.list();
        scheduleTaskConfig.startCron(list);
    }
}
