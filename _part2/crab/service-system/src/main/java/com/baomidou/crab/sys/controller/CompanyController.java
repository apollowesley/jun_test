package com.baomidou.crab.sys.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Company;
import com.baomidou.crab.sys.service.ICompanyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统公司表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-21
 */
@Api(tags = {"公司"})
@RestController
@RequestMapping("/sys/company")
public class CompanyController extends BaseController<ICompanyService, Company> {


    @GetMapping("/page")
    public R<IPage<Company>> page(Company company) {
        return success(baseService.page(getPage(), company));
    }


    @GetMapping("/list")
    public R<List<Company>> list(Company company) {
        return success(baseService.list(new QueryWrapper<>(company)));
    }


    @GetMapping("/list_effective")
    public R<List<Company>> listEffective() {
        return success(baseService.listEffective());
    }


    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id,
                             @RequestParam Integer status) {
        return success(baseService.updateStatus(id, status));
    }
}

