package com.jeasy.blog.controllers;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jeasy.base.controllers.BaseController;
import com.jeasy.base.controllers.ModelResult;
import com.jeasy.blog.model.Blog;
import com.jeasy.blog.service.BlogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author taomk
 * @version 1.0
 * @since 2015/06/22 16:08
 */
@Slf4j
@Path("blog")
public class BlogController extends BaseController {

	@Autowired
	private BlogService blogService;

	@Get("list")
	public String list(Blog blog) {
		List<Blog> blogList = blogService.find(blog);
    	return renderJson(new Gson().toJson(responseList(ModelResult.CODE_200, ModelResult.SUCCESS, blogList)));
    }

    @Get("page")
    public String page(Blog blog) {
    	int totalCount = blogService.count(blog);
    	List<Blog> blogList = blogService.page(blog, getOffset(), getPageSize());
        return renderJson(new Gson().toJson(responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, totalCount, blogList)));
	}

    @Post("add")
    public String add(Blog blog) {
        blogService.save(blog);
        return renderJson(new Gson().toJson(responseMessage(ModelResult.CODE_200, ModelResult.SUCCESS)));
    }

    @Get("{id:[0-9]+}")
    public String show(@Param("id") long id) {
        Blog blog = blogService.getById(id);
        return renderJson(new Gson().toJson(responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, blog)));
    }

	@Post("modify")
    public String modify(Blog blog) {
        blogService.modify(blog);
        return renderJson(new Gson().toJson(responseMessage(ModelResult.CODE_200, ModelResult.SUCCESS)));
    }

	@Post("{id:[0-9]+}")
    public String remove(@Param("id") long id) {
        blogService.remove(id);
        return renderJson(new Gson().toJson(responseMessage(ModelResult.CODE_200, ModelResult.SUCCESS)));
    }
}