package com.jeasy.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeasy.blog.dao.BlogDAO;
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
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDAO blogDAO;

	@Override
	public List<Blog> find(Blog blog) {
		return blogDAO.selectByParams(blog);
	}

    @Override
    public Blog getById(long id) {
    	return blogDAO.selectById(id);
    }

    @Override
    public List<Blog> findByIds(List<Long> ids) {
        return blogDAO.selectByIds(ids);
    }

    @Override
    public List<Blog> page(Blog blog, int offset, int size) {
    	return blogDAO.selectByParams(blog, offset, size);
    }

    @Override
    public int count(Blog blog) {
    	return blogDAO.countByParams(blog);
    }

    @Override
    public Blog getFirst(Blog blog) {
    	return blogDAO.selectFirst(blog);
    }

    @Override
    public long save(Blog blog) {
    	return blogDAO.insert(blog);
    }

    @Override
    public int saveBatch(List<Blog> blogList) {
    	return blogDAO.insertBatch(blogList);
    }

    @Override
    public long saveSelective(Blog blog) {
    	return blogDAO.insertSelective(blog);
    }

    @Override
    public int modify(Blog blog) {
    	return blogDAO.update(blog);
    }

    @Override
    public int modifySelective(Blog blog) {
    	return blogDAO.updateSelective(blog);
    }

    @Override
    public int remove(long id) {
    	return blogDAO.delete(id);
    }

    @Override
    public int removeBatch(List<Long> ids) {
    	return blogDAO.deleteBatch(ids);
    }
}