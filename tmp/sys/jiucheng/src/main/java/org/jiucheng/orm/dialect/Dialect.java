/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.orm.dialect;

import org.jiucheng.orm.Sql;

/**
 * 
 * @author jiucheng
 *
 */
public interface Dialect {
    
    public <T> void forSave(T entity, Sql sh);
    
    public <T> void forDelete(T entity, Sql sh);
    
    public <T> void forDeleteById(T entity, Sql sh);

    public <T> void forUpdateById(T entity, Sql sh);
    
    public <T> void forFind(T entity, Sql sh);

    public <T> void forFindById(T entity, Sql sh);

    public String forColumnLabel(String columnLabel);
}
