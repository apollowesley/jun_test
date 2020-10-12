package com.slavic.hors.orm.base;

import java.util.List;


public interface BaseMapper<Entry, ID> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Entry queryById(ID id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param entry 实例对象
     * @return 对象列表
     */
    List<Entry> queryAll(Entry entry);

    /**
     * 新增数据
     *
     * @param entry 实例对象
     * @return 影响行数
     */
    int insert(Entry entry);

    /**
     * 修改数据
     *
     * @param entry 实例对象
     * @return 影响行数
     */
    int updateById(Entry entry);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(ID id);

}
