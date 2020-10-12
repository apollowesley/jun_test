package com.cnit1818.base;

import com.ibatis.sqlmap.client.SqlMapClient;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: MaYong
 * Date: 13-3-20
 * Time: 下午1:06
 * To change this template use File | Settings | File Templates.
 */
public class SimpleGenericDAO <T, PK extends Serializable> extends GenericDAO<T, PK> {

    public SimpleGenericDAO() {
        super();
    }

    /**
     * 用于省略Dao层, Service层直接使用通用GenericDAO的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
     */
    public SimpleGenericDAO(final SqlMapClient sqlMapClient, final Class<T> entityClass) {
        super(sqlMapClient, entityClass);
    }

}
