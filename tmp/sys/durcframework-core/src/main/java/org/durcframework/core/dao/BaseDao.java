package org.durcframework.core.dao;

import org.durcframework.core.Edit;

/**
 * DAO类,其它DAO类都要继承该类
 * @author tanghc
 * 
 * @param <Entity>
 */
public interface BaseDao<Entity> extends Edit<Entity> {
}