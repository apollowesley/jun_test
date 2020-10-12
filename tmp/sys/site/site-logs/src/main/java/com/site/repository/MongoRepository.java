package com.site.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.site.common.utils.Page;

public interface MongoRepository<T> {

    public Class<T> getEntityClass();

    public void insert(Object entity);

    public List<T> find(Query query);

    public T findOne(String id);

    public List<T> findByRegex(String regex);

    public List<T> findAll();

    public Page<T> findPagination(Page<T> page, Query query);

    public long count();

    public long count(Query query);

    public void updateEntity(Object entity);

    public void removeOne(String id);

}
