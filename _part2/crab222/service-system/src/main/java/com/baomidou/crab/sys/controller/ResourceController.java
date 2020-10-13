package com.baomidou.crab.sys.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Resource;
import com.baomidou.crab.sys.service.IResourceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统资源表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-09-13
 */
@Api(tags = {"资源"})
@RestController
@RequestMapping("/sys/resource")
public class ResourceController extends BaseController<IResourceService, Resource> {

    @GetMapping("/page")
    public R<IPage<Resource>> page(Resource resource) {
        return success(baseService.page(getPage(), resource));
    }

    @GetMapping("/list")
    public R<List<Resource>> list(Resource resource) {
        return success(baseService.list(Wrappers.<Resource>lambdaQuery()
                .setEntity(resource).orderByDesc(Resource::getSort)));
    }

    @GetMapping("/list_effective")
    public R<List<Resource>> listEffective() {
        return success(baseService.listEffective());
    }

    @GetMapping("/menu")
    public R<List<Resource>> listMenu() {
        return success(baseService.listMenuByUserId(getLoginUserId()));
    }

    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id,
                             @RequestParam Integer status) {
        return success(baseService.changeStatus(id, status));
    }
}
