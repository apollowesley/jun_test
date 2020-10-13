package com.baomidou.crab.sys.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.entity.Post;
import com.baomidou.crab.sys.service.IPostService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统岗位表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-10-21
 */
@Api(tags = {"岗位"})
@RestController
@RequestMapping("/sys/post")
public class PostController extends BaseController<IPostService, Post> {


    @GetMapping("/page")
    public R<IPage<Post>> page(Post post) {
        return success(baseService.page(getPage(), post));
    }


    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id,
                             @RequestParam Integer status) {
        return success(baseService.updateStatus(id, status));
    }
}

