package com.dtdream.rdic.saas.hibernate;

/**
 * Created by Ftech on 2015/6/5.
 */

/**
 * 更新数据库通用接口
 * @param <T> PO类型
 */
public interface HibernateUpdater<T> {
    /**
     * 更新数据记录
     * @param po        待更新的记录
     * @param index     本次更新的记录索引（从0递增）
     * @param objects   更新记录需要用到的参数
     */
    public void update(T po, int index, Object... objects);
}
