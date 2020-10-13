package com.baomidou.crab.v1.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.v1.entity.Article;
import com.baomidou.crab.v1.service.IArticleService;
import com.baomidou.crab.core.web.BaseController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;


/**
* <p>
* 文章表 前端控制器
* </p>
*
* @author jobob
* @since 2019-02-07
*/
@Api(tags = {"v1", "文章表"})
@RestController
@RequestMapping("/v1/article")
public class ArticleController extends BaseController<IArticleService, Article> {

    @GetMapping("/page")
    public R<IPage<Article>> page(Article article) {
        return success(baseService.page(getPage(), new QueryWrapper<>(article)));
    }
}
