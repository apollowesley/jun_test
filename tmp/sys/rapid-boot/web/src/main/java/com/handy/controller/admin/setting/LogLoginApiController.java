package com.handy.controller.admin.setting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.handy.common.vo.PageVO;
import com.handy.service.entity.log.LogLogin;
import com.handy.service.service.log.ILogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author handy
 * @Description: {登录日志}
 * @date 2019/9/3 16:32
 */
@RestController
@RequestMapping("/api/admin/setting/logLogin")
@Api(value = "登录日志")
public class LogLoginApiController {
    @Autowired
    private ILogLoginService logLoginService;

    @GetMapping("/list")
    @ApiOperation(value = "获取列表数据")
    public PageVO list(@ApiParam(name = "page", value = "每页页数") Long page,
                       @ApiParam(name = "limit", value = "每页条数") Long limit) {
        val wrapper = new QueryWrapper<LogLogin>();
        LambdaQueryWrapper<LogLogin> queryWrapper = wrapper.lambda();
        queryWrapper.orderByDesc(LogLogin::getCreateTime);
        IPage<LogLogin> logLoginIPage = logLoginService.page(new Page<>(page, limit), wrapper);
        return PageVO.pageVO(logLoginIPage.getRecords(), logLoginIPage.getTotal());
    }
}
