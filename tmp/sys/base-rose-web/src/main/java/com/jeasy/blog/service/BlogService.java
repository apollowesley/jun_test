package com.jeasy.blog.service;

import java.util.List;

import com.jeasy.blog.model.Blog;

/**
 * 
 * @author taomk
 * @version 1.0
 * @since 2015/06/22 16:08
 */
public interface BlogService {

	/**
	 * 查询
	 */
	public List<Blog> find(Blog blog);

    /**
     * ID查询
     */
    public Blog getById(long id);

    /**
     * ID批量查询
     */
    public List<Blog> findByIds(List<Long> ids);

    /**
     * 参数分页查询
     */
    public List<Blog> page(Blog blog, int offset, int size);

    /**
     * 参数查询总数
     */
    public int count(Blog blog);

    /**
     * First查询
     */
    public Blog getFirst(Blog blog);

    /**
     * 保存
     */
    public long save(Blog blog);

    /**
      * 批量保存
      */
	public int saveBatch(List<Blog> blogList);

	/**
	 * 选择保存
	 */
	public long saveSelective(Blog blog);

    /**
     * 修改
     */
    public int modify(Blog blog);

    /**
     * 选择修改
     */
    public int modifySelective(Blog blog);

    /**
     * 删除
     */
    public int remove(long id);

    /**
     * 批量删除
     */
    public int removeBatch(List<Long> ids);
}
