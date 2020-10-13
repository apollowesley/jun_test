package com.baomidou.crab.v1.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.v1.entity.Tag;
import com.baomidou.crab.v1.service.ITagService;
import com.baomidou.crab.core.web.BaseController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;


/**
* <p>
* 标签表 前端控制器
* </p>
*
* @author jobob
* @since 2019-02-07
*/
@Api(tags = {"v1", "标签表"})
@RestController
@RequestMapping("/v1/tag")
public class TagController extends BaseController<ITagService, Tag> {

    @GetMapping("/page")
    public R<IPage<Tag>> page(Tag tag) {
        return success(baseService.page(getPage(), new QueryWrapper<>(tag)));
    }

    @Login(action = Action.Skip)
    @GetMapping("/list")
    public R<List<Tag>> list() {
        return success(baseService.list(null));
    }
}

