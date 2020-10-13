package com.baomidou.crab.sys.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Dict;
import com.baomidou.crab.sys.service.IDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统字典表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
@Api(tags = {"字典"})
@RestController
@RequestMapping("/sys/dict")
public class DictController extends BaseController<IDictService, Dict> {

    @GetMapping("/page")
    public R<IPage<Dict>> page(Dict dict) {
        return success(baseService.page(getPage(), dict));
    }

    @GetMapping("/list_{parentCode}")
    public R<List<Dict>> listByParentCode(@PathVariable("parentCode") String parentCode) {
        return success(baseService.listByParentCode(parentCode));
    }

    @PutMapping("/sys_{id}")
    public R<Boolean> sys(@PathVariable("id") Long id, @RequestParam Integer sys) {
        return success(baseService.updateSys(id, sys));
    }

    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id, @RequestParam Integer status) {
        return success(baseService.updateStatus(id, status));
    }
}
