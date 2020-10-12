package com.site.repository;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.site.common.utils.Page;

@Repository
public abstract class MongoRepositoryImpl<T> implements MongoRepository<T> {

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public void insert(Object entity) {
        mongoTemplate.insert(entity);
    }

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), getEntityClass());
    }

    @Override
    public List<T> findByRegex(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria("name").regex(pattern.toString());
        return mongoTemplate.find(new Query(criteria), getEntityClass());
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(getEntityClass());
    }

    @Override
    public Page<T> findPagination(Page<T> page, Query query) {
        int pageSize = page.getPageSize();
        page.setTotalCount(this.count(query));
        query.skip((page.getCurrentPage() - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setResultList(rows);
        return page;
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), getEntityClass());
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, getEntityClass());
    }

    @Override
    public void updateEntity(Object entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public void removeOne(String id) {
        Criteria criteria = Criteria.where("id").in(id);
        if (criteria != null) {
            Query query = new Query(criteria);
            if (query != null && mongoTemplate.findOne(query, getEntityClass()) != null) {
                mongoTemplate.remove(mongoTemplate.findOne(query, getEntityClass()));
            }
        }
    }

}
