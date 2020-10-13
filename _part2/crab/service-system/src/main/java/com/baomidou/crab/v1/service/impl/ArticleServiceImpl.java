package com.baomidou.crab.v1.service.impl;

import com.baomidou.crab.v1.entity.Article;
import com.baomidou.crab.v1.mapper.ArticleMapper;
import com.baomidou.crab.v1.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
