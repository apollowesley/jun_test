package com.baomidou.crab.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Log;
import com.baomidou.crab.sys.service.ILogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-06
 */
@Api(tags = {"日志"})
@RestController
@RequestMapping("/sys/log")
public class LogController extends BaseController<ILogService, Log> {


    @GetMapping("/page")
    public R<IPage<Log>> page(Log log) {
        return success(baseService.page(getPage(), Wrappers.query(log)
                .orderByDesc("create_time")));
    }
}
