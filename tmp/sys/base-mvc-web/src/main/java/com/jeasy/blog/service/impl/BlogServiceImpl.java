package com.jeasy.blog.service.impl;

import org.springframework.stereotype.Service;

import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.blog.dao.BlogDAO;
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
@Service
public class BlogServiceImpl extends BaseServiceImpl<BlogDAO, Blog> implements BlogService {

}