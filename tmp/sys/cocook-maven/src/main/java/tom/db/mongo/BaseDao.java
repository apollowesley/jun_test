package tom.db.mongo;

import java.util.List;
import java.util.Map;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public interface BaseDao {
	
	public String insert(String col, Map<String, ? extends Object> data) ;

	public String insertAsBean(String col, Object bean) ;

	public WriteResult insertAsBeans(String col, List<Object> beans);

	public WriteResult insert(String col, List<DBObject> list) ;

	public WriteResult insert(String col, DBObject... obj) ;

	public String save(String col, DBObject obj) ;

	/** 修改 */
	public WriteResult update(String col, DBObject q, DBObject o, boolean upsert, boolean multi);

	/** 单条修改 */
	public WriteResult update(String col, DBObject q, DBObject o) ;

	/** 单条单列,其他列如果没有赋值不修改 */
	public WriteResult updateSingle(String col, DBObject q, DBObject o);

	/** 批量修改 */
	public WriteResult updateMulti(String col, DBObject q, DBObject o) ;

	/** 批量修改 单列,其他列如果没有赋值不修改 */
	public WriteResult updateMultiSingle(String col, DBObject q, DBObject o);
	/** 批量修改 单列,其他列如果没有赋值不修改 */
	public WriteResult updateMulti(String col, Map<String, ? extends Object> q, Map<String, ? extends Object> o);
	/** 按id修改, 只修改指定的列 */
	public WriteResult updateByidAsBean(String col, String id, Object bean);

	public WriteResult updateByid(String col, String id, DBObject o);
	public WriteResult remove(String col, DBObject o);

	public WriteResult deleteById(String col, String id);

	public WriteResult delete(String col, String query) ;

	public WriteResult delete(String col, Map<String, ? extends Object> query) ;

	/**
	 * 查找
	 * 
	 * @param col
	 * @param o
	 * @return
	 */
	public List<?> distinct(String col, String key, DBObject query);

	public DBObject findOne(String col, DBObject o);

	public DBObject findOne(String col, DBObject query, DBObject keys);

	public List<DBObject> DBCursor2list(DBCursor cur) ;

	public List<DBObject> findList(String col, DBObject query);

	public List<DBObject> findOneList(String col, DBObject query, DBObject key);

	public List<DBObject> findList(String col, DBObject query, DBObject sort) ;

	public List<DBObject> findList(String col, DBObject query, DBObject sort, int start, int limit);

	public DBObject queryById(String col, String id);

	public <T> T getBeanById(String col, String id, Class<T> clazz);

	public <T> T getBean(String col, DBObject query, Class<T> clazz);

	public <T> List<T> getList(String col, DBObject query, Class<T> clazz);

	public <T> List<T> getList(String col, DBObject query, DBObject sort, Class<T> clazz) ;

	public <T> List<T> getList(String col, DBObject query, DBObject sort, int start, int limit, Class<T> clazz);

	public long count(String col, DBObject query);

	public DBCollection getConllection(String col);

	/* public DBObject group() */

	/* public MapReduceOutput mapReduce */

	/* public List distinct */


}
