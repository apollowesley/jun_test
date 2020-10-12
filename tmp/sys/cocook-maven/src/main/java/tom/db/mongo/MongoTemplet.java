package tom.db.mongo;

import java.util.List;
import java.util.Map;

import tom.db.mongo.BaseDao;
import tom.db.mongo.MongoDao;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;


public class MongoTemplet implements BaseDao {

	private MongoDao dao = MongoDao.getInstance();
	
	public void setDB(String _db){
		dao.set_DB(_db);
	}

	public String insert(String col, Map<String, ? extends Object> data) {
		return dao.insert(col, data);
	}

	public String insertAsBean(String col, Object bean) {
		return dao.insertAsBean(col, bean);
	}

	public WriteResult insertAsBeans(String col, List<Object> beans) {
		return dao.insertAsBeans(col, beans);
	}

	public WriteResult insert(String col, List<DBObject> list) {
		return dao.insert(col, list);
	}

	public WriteResult insert(String col, DBObject... obj) {
		return dao.insert(col, obj);
	}

	public String save(String col, DBObject obj) {
		return dao.save(col, obj);
	}

	public WriteResult update(String col, DBObject q, DBObject o, boolean upsert, boolean multi) {
		return dao.update(col, q, o, upsert, multi);
	}

	public WriteResult update(String col, DBObject q, DBObject o) {
		return dao.update(col, q, o);
	}

	public WriteResult updateSingle(String col, DBObject q, DBObject o) {
		return dao.updateSingle(col, q, o);
	}

	public WriteResult updateMulti(String col, DBObject q, DBObject o) {
		return dao.updateMulti(col, q, o);
	}

	public WriteResult updateMultiSingle(String col, DBObject q, DBObject o) {
		return dao.updateMultiSingle(col, q, o);
	}

	public WriteResult updateMulti(String col, Map<String, ? extends Object> q, Map<String, ? extends Object> o) {
		return dao.updateMulti(col, q, o);
	}

	public WriteResult updateByidAsBean(String col, String id, Object bean) {
		return dao.updateByidAsBean(col, id, bean);
	}

	public WriteResult updateByid(String col, String id, DBObject o) {
		return dao.updateByid(col, id, o);
	}

	public WriteResult remove(String col, DBObject o) {
		return dao.remove(col, o);
	}

	public WriteResult deleteById(String col, String id) {
		return dao.deleteById(col, id);
	}

	public WriteResult delete(String col, String query) {
		return dao.delete(col, query);
	}

	public WriteResult delete(String col, Map<String, ? extends Object> query) {
		return dao.delete(col, query);
	}

	public List<?> distinct(String col, String key, DBObject query) {
		return dao.distinct(col, key, query);
	}

	public DBObject findOne(String col, DBObject o) {
		return dao.findOne(col, o);
	}

	public DBObject findOne(String col, DBObject query, DBObject keys) {
		return dao.findOne(col, query, keys);
	}

	public List<DBObject> DBCursor2list(DBCursor cur) {
		return dao.DBCursor2list(cur);
	}

	public List<DBObject> findList(String col, DBObject query) {
		return dao.findList(col, query);
	}

	public List<DBObject> findOneList(String col, DBObject query, DBObject key) {
		return dao.findOneList(col, query, key);
	}

	public List<DBObject> findList(String col, DBObject query, DBObject sort) {
		return dao.findList(col, query, sort);
	}

	public List<DBObject> findList(String col, DBObject query, DBObject sort, int start, int limit) {
		return dao.findList(col, query, sort, start, limit);
	}

	public DBObject queryById(String col, String id) {
		return dao.queryById(col, id);
	}

	public <T> T getBeanById(String col, String id, Class<T> clazz) {
		return dao.getBeanById(col, id, clazz);
	}

	public <T> T getBean(String col, DBObject query, Class<T> clazz) {
		return dao.getBean(col, query, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, Class<T> clazz) {
		return dao.getList(col, query, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, DBObject sort, Class<T> clazz) {
		return dao.getList(col, query, sort, clazz);
	}

	public <T> List<T> getList(String col, DBObject query, DBObject sort, int start, int limit, Class<T> clazz) {
		return dao.getList(col, query, sort, start, limit, clazz);
	}

	public long count(String col, DBObject query) {
		return dao.count(col, query);
	}

	public DBCollection getConllection(String col) {
		return dao.getConllection(col);
	}
	
	/*public void page(String col, DBObject query, DBObject sort, tom.bean.Page<List<DBObject>> page){
		long totalcount = count(col, query);
		page.setTotalCount(totalcount);
		page.setTotalPage(totalcount%page.getPageSize()==0 ? totalcount/page.getPageSize() : totalcount/page.getPageSize()+1);
		if(page.getTotalPage()==0) page.setRows(Collections.EMPTY_LIST);
		List<DBObject> rows=  this.findList(col, query,sort,(page.getPageNumber()-1)*page.getPageSize(),page.getPageSize());
		page.setRows(rows);
	}*/
}
