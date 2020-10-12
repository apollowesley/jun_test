package com.antdsp.dao.jpa;

import org.springframework.stereotype.Repository;

import com.antdsp.data.entity.Article;

@Repository(value="articleJpa")
public interface ArticleJpa extends AntdspBaseRepository<Article, Long>{

}
