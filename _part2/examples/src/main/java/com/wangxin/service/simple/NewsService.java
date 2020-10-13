package com.wangxin.service.simple;

import com.github.pagehelper.PageInfo;
import com.wangxin.entity.simple.News;

/** 
 * @Description 新闻接口类
 * @author 王鑫 
 * @date Mar 16, 2017 5:19:14 PM  
 */
public interface NewsService {

    // ---- master
    public boolean addNews(News news);

    public boolean editNews(News news);

    public boolean deleteNewsById(String id);

    public News findNewsById(String newsId);

    public PageInfo<News> findNewsByPage(Integer pageNum, String keywords);

    // ---- slave
    public boolean addSlaveNews(News news);

    public boolean editSlaveNews(News news);

    public boolean deleteSlaveNewById(String id);

    public News findSlaveNewsById(String newsId);

    public PageInfo<News> findSlaveNewsByPage(Integer pageNum, String keywords);

}