package com.guest.assessment.service;



import java.util.List;

public interface BaseService<T> {

    T add(T entity);

    void delete(T entity);

    T update(T entity);

    List<T> getList();

    T findById(String id);

}
