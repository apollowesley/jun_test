package com.jeasy.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeasy.base.controller.BaseController;
import com.jeasy.blog.model.Blog;
import com.jeasy.blog.service.BlogService;

import lombok.extern.log4j.Log4j;

/**
 * 
 * @author taomk
 * @version 1.0
 * @since 2015/06/21 14:52
 */
@Log4j
@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController<BlogService, Blog> {

}