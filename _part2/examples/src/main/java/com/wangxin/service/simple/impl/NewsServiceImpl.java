package com.wangxin.service.simple.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxin.common.constants.Constants;
import com.wangxin.common.framework.datasource.DataSourceEnum;
import com.wangxin.common.framework.datasource.DataSourceRouter;
import com.wangxin.common.framework.key.FactoryAboutKey;
import com.wangxin.common.framework.key.table.MasterTablesEnum;
import com.wangxin.common.framework.key.table.SlaveTablesEnum;
import com.wangxin.entity.simple.News;
import com.wangxin.mapper.simple.NewsMapper;
import com.wangxin.service.simple.NewsService;

/** 
 * @Description 新闻接口实现类
 * @author 王鑫 
 * @date Mar 16, 2017 5:19:24 PM  
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsMapper newsMapper;

    // ---- master

    @DataSourceRouter(DataSourceEnum.master)
    @Transactional
    @Override
    public boolean addNews(News news) {
        if (news != null) {
            news.setId(FactoryAboutKey.getPkByMasterDB(MasterTablesEnum.T_NEWS));
            news.setCreateTime(Calendar.getInstance().getTime());
            int flag = newsMapper.insert(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public boolean editNews(News news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = newsMapper.update(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @Transactional
    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public boolean deleteNewsById(String id) {
        if (StringUtils.isNotBlank(id)) {
            int flag = newsMapper.delete(id);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public News findNewsById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return newsMapper.findById(id);
    }

    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public PageInfo<News> findNewsByPage(Integer pageNum, String keywords) {
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        List<News> news = newsMapper.findNewsByPage(keywords);
        PageInfo<News> page = new PageInfo<News>(news);
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

    // ---- slave

    @DataSourceRouter(DataSourceEnum.slave)
    @Transactional
    @Override
    public boolean addSlaveNews(News news) {
        if (news != null) {
            news.setId(FactoryAboutKey.getPkBySlaveDB(SlaveTablesEnum.T_NEWS));
            news.setCreateTime(Calendar.getInstance().getTime());
            int flag = newsMapper.insert(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public boolean editSlaveNews(News news) {
        if (news != null && StringUtils.isNotBlank(news.getId())) {
            int flag = newsMapper.update(news);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @Transactional
    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public boolean deleteSlaveNewById(String id) {
        if (StringUtils.isNotBlank(id)) {
            int flag = newsMapper.delete(id);
            if (flag == 1)
                return true;
            else
                return false;
        } else
            return false;
    }

    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public News findSlaveNewsById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        else
            return newsMapper.findById(id);
    }

    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public PageInfo<News> findSlaveNewsByPage(Integer pageNum, String keywords) {
        if (pageNum == null)
            pageNum = 1;
        PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
        List<News> news = newsMapper.findNewsByPage(keywords);
        PageInfo<News> page = new PageInfo<News>(news);
        log.info("# 查询默认数据库 page.toString()={}", page.toString());
        return page;
    }

}
