package com.handy.controller.admin.workflow;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.activiti.service.IWorkFlowRepositoryService;
import com.handy.activiti.service.IWorkFlowRuntimeService;
import com.handy.activiti.service.IWorkFlowTaskService;
import com.handy.common.constants.WorkFlowStatusEnum;
import com.handy.common.vo.PageVO;
import com.handy.common.vo.ResultVO;
import com.handy.controller.BaseController;
import com.handy.param.workflow.WorkFlowParam;
import com.handy.service.entity.license.LicenseEvent;
import com.handy.service.entity.license.LicenseForm;
import com.handy.service.service.license.ILicenseEventService;
import com.handy.service.service.license.ILicenseFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/12 11:40
 */
@RestController
@RequestMapping("/api/admin/workflow")
@Api(value = "流程列表")
public class WorkFlowApiController extends BaseController {
    @Autowired
    private IWorkFlowRepositoryService workFlowRepositoryService;
    @Autowired
    private IWorkFlowTaskService workFlowTaskService;
    @Autowired
    private IWorkFlowRuntimeService workFlowRuntimeService;
    @Autowired
    private ILicenseEventService licenseEventService;
    @Autowired
    private ILicenseFormService licenseFormService;

    @GetMapping("/list")
    @ApiOperation(value = "流程列表")
    public PageVO list(@ApiParam(name = "page", value = "每页页数") Integer page,
                       @ApiParam(name = "limit", value = "每页条数") Integer limit) {
        val processList = workFlowRepositoryService.getProcessList(page, limit);
        List<WorkFlowParam> workFlowParams = new ArrayList<>();
        for (ProcessDefinition processDefinition : processList) {
            val param = new WorkFlowParam();
            param.setId(processDefinition.getId());
            param.setName(processDefinition.getName());
            param.setDescription(processDefinition.getDescription());
            param.setKey(processDefinition.getKey());
            param.setVersion(processDefinition.getVersion());
            workFlowParams.add(param);
        }
        return PageVO.pageVO(workFlowParams, (long) workFlowParams.size());
    }

    @GetMapping("/list/view")
    @ApiOperation(value = "流程图")
    public void modelList(HttpServletResponse response,
                          @ApiParam(name = "processDefinitionId", value = "流程ID") String processDefinitionId) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] bytes = workFlowRepositoryService.definitionImage(processDefinitionId);
            inputStream = new ByteArrayInputStream(bytes);
            outputStream = response.getOutputStream();
            BufferedImage img = ImageIO.read(inputStream);
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            // 将图像输出到S输出流中。
            ImageIO.write(img, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/model/list")
    @ApiOperation(value = "模型列表")
    public PageVO modelList(@ApiParam(name = "page", value = "每页页数") Integer page,
                            @ApiParam(name = "limit", value = "每页条数") Integer limit) {
        val deployments = workFlowRepositoryService.deployList(page, limit);
        List<WorkFlowParam> workFlowParams = new ArrayList<>();
        for (Deployment deployment : deployments) {
            val param = new WorkFlowParam();
            param.setId(deployment.getId());
            param.setName(deployment.getName());
            param.setDeploymentTime(deployment.getDeploymentTime());
            param.setKey(deployment.getKey());
            workFlowParams.add(param);
        }
        return PageVO.pageVO(workFlowParams, (long) workFlowParams.size());
    }

    @PostMapping("/confirm/add")
    @ApiOperation(value = "发起流程")
    public ResultVO add(HttpSession session, LicenseForm form,
                        @ApiParam(name = "approveId", value = "审批人id") Long approveId) {
        val user = getLoginUser(session);
        form.setCreator(user.getCode());
        val rst = licenseFormService.save(form);
        if (rst) {
            val betweenDay = DateUtil.between(form.getStartTime(), form.getEndTime(), DateUnit.DAY);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("user", approveId.toString());
            paramsMap.put("day", betweenDay);
            ProcessInstance leave = workFlowRuntimeService.startProcess("leave", paramsMap);
            val event = new LicenseEvent();
            event.setLicenseId(1L);
            event.setLicenseName("请假流程");
            event.setAccountId(user.getId());
            event.setAccountName(user.getName());
            event.setApproveId(approveId);
            event.setStartTime(new Date());
            event.setStatusId(WorkFlowStatusEnum.APPLY.getId());
            event.setStatusName(WorkFlowStatusEnum.APPLY.getName());
            event.setFormId(form.getId());
            event.setCreator(user.getCode());
            event.setWorkFlowId(leave.getId());
            boolean save = licenseEventService.save(event);
            return save ? ResultVO.success("发起流程成功") : ResultVO.failure("发起流程失败");
        }
        return ResultVO.success("发起流程失败");
    }

    @GetMapping("/task/list")
    @ApiOperation(value = "待办任务")
    public PageVO taskList(HttpSession session,
                           @ApiParam(name = "statusId", value = "状态") Long statusId,
                           @ApiParam(name = "page", value = "每页页数") Integer page,
                           @ApiParam(name = "limit", value = "每页条数") Integer limit) {
        val user = getLoginUser(session);
        val wrapper = new QueryWrapper<LicenseEvent>();
        LambdaQueryWrapper<LicenseEvent> queryWrapper = wrapper.lambda();
        queryWrapper.eq(LicenseEvent::getApproveId, user.getId()).eq(LicenseEvent::getStatusId, statusId);
        IPage<LicenseEvent> eventIPage = licenseEventService.page(new Page<>(page, limit), wrapper);
        return PageVO.pageVO(eventIPage.getRecords(), eventIPage.getTotal());
    }

    @PostMapping("/confirm/approve")
    @ApiOperation(value = "确定审批")
    public ResultVO confirmApprove(HttpSession session,
                                   @ApiParam(name = "taskId", value = "事项id") Long eventId,
                                   @ApiParam(name = "processId", value = "流程id") String processId,
                                   @ApiParam(name = "opinion", value = "审批状态") Boolean opinion) {
        val wrapper = new QueryWrapper<LicenseEvent>();
        LambdaQueryWrapper<LicenseEvent> queryWrapper = wrapper.lambda();
        queryWrapper.eq(LicenseEvent::getId, eventId);
        val licenseEvent = licenseEventService.getOne(wrapper);
        if (!WorkFlowStatusEnum.APPLY.getId().equals(licenseEvent.getStatusId())) {
            return ResultVO.failure("该流程已经审批过!请不要重复审批");
        }
        // 完成流程进行下一步
        workFlowTaskService.complete(processId);
        // 完成流程保存信息
        licenseEvent.setApproveTime(new Date());
        licenseEvent.setMender(getLoginUser(session).getName());
        if (opinion) {
            licenseEvent.setStatusId(WorkFlowStatusEnum.PASS.getId());
            licenseEvent.setStatusName(WorkFlowStatusEnum.PASS.getName());
        } else {
            licenseEvent.setStatusId(WorkFlowStatusEnum.REJECT.getId());
            licenseEvent.setStatusName(WorkFlowStatusEnum.REJECT.getName());
        }
        val rst = licenseEventService.updateById(licenseEvent);
        return rst ? ResultVO.success("审批完成") : ResultVO.failure("审批出错,请联系管理员");
    }
}
