package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.util.List;

import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.util.TableUtil;

public abstract class BaseServiceImpl implements IBaseService {

	public abstract IBaseDao getBaseDao();

	public <T> Serializable save(T entity) {
		return getBaseDao().save(entity);
	}

	public <T> Serializable saveOrUpdate(T entity) {
		return getBaseDao().saveOrUpdate(entity);
	}

	public <T> boolean update(T entity) {
		return getBaseDao().update(entity);
	}

	public <T> int delete(T entity) {
		return getBaseDao().delete(entity);
	}

	public <T> List<T> list(T entity) {
		return getBaseDao().list(entity);
	}

    public <T> T get(Class<T> clazz, Serializable id) {
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new DataAccessException(e);
        } catch (IllegalAccessException e) {
            throw new DataAccessException(e);
        }
        TableUtil.setPrimayKeyValue(obj, id);
        List<T> rs = list(obj);
        if(rs.size() > 0) {
            return rs.get(0);
        }
        return null;
    }
}
