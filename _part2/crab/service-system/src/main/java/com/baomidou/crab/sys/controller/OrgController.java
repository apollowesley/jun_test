package com.baomidou.crab.sys.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Org;
import com.baomidou.crab.sys.service.IOrgService;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统机构表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-11-07
 */
@Api(tags = {"机构"})
@RestController
@RequestMapping("/sys/org")
public class OrgController extends BaseController<IOrgService, Org> {


    @GetMapping("/list_{companyId}")
    public R<List<Org>> listAll(@PathVariable("companyId") Long companyId) {
        return success(baseService.listByCompanyId(companyId));
    }
}
