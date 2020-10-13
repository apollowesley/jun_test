package tom.cache;

import java.sql.SQLException;

/**
 * 回调函数接口
 * @author
 */
public interface CacheInvoker {
	public Object callback(Object...args) throws SQLException; 
}
