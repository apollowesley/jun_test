package com.site.framework.key.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.site.framework.key.model.Key;

@Repository("keyMapper")
public interface KeyMapper {

    /**
     * @return 返回key集合
     */
    List<Key> getTableValues(List<Key> keys);

    /**
     * @return 返回key集合(只存储表名)
     */
    List<Key> getTables();

}
