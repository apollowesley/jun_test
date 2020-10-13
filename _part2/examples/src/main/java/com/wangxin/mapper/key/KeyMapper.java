package com.wangxin.mapper.key;

import java.util.List;

import com.wangxin.common.framework.key.entity.Key;

public interface KeyMapper {

    /**
     * @return 返回key集合
     */
    public List<Key> getTableValues(List<Key> keys);

    /**
     * @return 返回key集合(只存储表名)
     */
    public List<Key> getTablesByOracle();
    
    /**
     * @return 返回key集合(只存储表名)
     */
    public List<Key> getTablesByMySQL();
    
    /**
     * @return 返回key集合(只存储表名)
     */
    public List<Key> getTablesBySQLite();

}
