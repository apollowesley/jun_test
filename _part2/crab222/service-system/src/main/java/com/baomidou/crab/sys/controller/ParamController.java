package com.baomidou.crab.sys.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Param;
import com.baomidou.crab.sys.service.IParamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统参数表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
@Api(tags = {"参数"})
@RestController
@RequestMapping("/sys/param")
public class ParamController extends BaseController<IParamService, Param> {


    @GetMapping("/page")
    public R<IPage<Param>> page(Param param) {
        return success(baseService.page(getPage(), param));
    }


    @PutMapping("/sys_{id}")
    public R<Boolean> sys(@PathVariable("id") Long id, @RequestParam Integer sys) {
        return success(baseService.updateSys(id, sys));
    }
}
