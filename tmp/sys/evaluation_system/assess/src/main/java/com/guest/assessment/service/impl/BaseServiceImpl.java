package com.guest.assessment.service.impl;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.entity.Indicator;
import com.guest.assessment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    JpaRepository<T, String> repository;

    @Override
    public T add(T entity) {
        return (T) repository.save(entity);
    }

    @Override
    public void delete(T entity) {
        repository.save(entity);
    }

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> getList() {
        return repository.findAll();
    }

    @Override
    public T findById(String id) {
        return repository.getOne(id);
    }






}
