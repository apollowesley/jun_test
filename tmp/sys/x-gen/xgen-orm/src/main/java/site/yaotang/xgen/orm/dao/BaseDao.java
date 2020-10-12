package site.yaotang.xgen.orm.dao;

import java.io.Serializable;
import java.util.List;

import site.yaotang.xgen.orm.sqltools.DetachCriteria;

/**
 * 书本数据库操作接口
 * @author hyt
 * @date 2017年12月31日
 */
public interface BaseDao {

	public void save(Object obj);

	public void update(Object obj);

	public void delete(Object obj);

	public Object query(String sql);

	public Object queryFromCache(String name, String sql, long seconds);

	public List<?> queryList(String sql);

	public List<?> queryList(DetachCriteria dc);
}
